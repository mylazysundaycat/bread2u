package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.post.dto.ApiResponse;
import com.daegeon.bread2u.module.post.dto.ApiResponseWrapper;
import com.daegeon.bread2u.module.post.dto.BreadRequest;
import com.daegeon.bread2u.module.post.dto.BreadResponse;
import com.daegeon.bread2u.module.post.entity.Bread;
import com.daegeon.bread2u.module.post.repository.BreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreadService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://www.seogu.go.kr/seoguAPI/3660000/getBakryStts";
    private final BreadRepository breadRepository;

    //API를 모두 데이터베이스에 저장
    public void saveOpenBreadApi() {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .build()
                .toUriString();
        ApiResponseWrapper responseWrapper = restTemplate.getForObject(url, ApiResponseWrapper.class);

        if (responseWrapper == null || responseWrapper.getResponse() == null || responseWrapper.getResponse().getBody() == null) {
            throw new RuntimeException("API 호출 실패 또는 응답 데이터가 없습니다.");
        }

        Set<String> excludedKeywords = Set.of("파리바게뜨", "파리바게트","뚜레","자연드림","손만두");

        List<Bread> breads = responseWrapper.getResponse().getBody().getItems().stream()
                .filter(response ->
                        excludedKeywords.stream().noneMatch(keyword -> response.getBssh_nm().contains(keyword))
                )
                .map(response -> new Bread(
                response.getBssh_nm(),           // storeName
                response.getLnm_adrs(),          // address (지번)
                response.getRn_adrs(),           // roadAddress (도로명)
                response.getTelno(),             // phone
                response.getLa(),    // latitude
                response.getLo(),    // longitude
                response.getData_stdr_de()       // standardDate
        )).collect(Collectors.toList());
        // DB에 저장
        breadRepository.saveAll(breads);
    }

    public List<BreadResponse> getBreadList() {
        List<Bread> bread = breadRepository.findAll();
        List<BreadResponse> breadResponses = bread.stream().map(res -> BreadResponse.from(res)).collect(Collectors.toList());
        return breadResponses;
    }




}
