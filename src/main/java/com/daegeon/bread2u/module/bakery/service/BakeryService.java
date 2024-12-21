package com.daegeon.bread2u.module.bakery.service;


import com.daegeon.bread2u.module.bakery.dto.openapi.ApiResponseWrapper;
import com.daegeon.bread2u.module.bakery.dto.BakeryResponse;
import com.daegeon.bread2u.module.bakery.entity.Bakery;
import com.daegeon.bread2u.module.bakery.repository.BakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BakeryService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://www.seogu.go.kr/seoguAPI/3660000/getBakryStts";
    private final BakeryRepository bakeryRepository;

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

        List<Bakery> bakeries = responseWrapper.getResponse().getBody().getItems().stream()
                .filter(response ->
                        excludedKeywords.stream().noneMatch(keyword -> response.getBssh_nm().contains(keyword))
                )
                .map(response -> new Bakery(
                response.getBssh_nm(),           // storeName
                response.getLnm_adrs(),          // address
                response.getRn_adrs(),           // roadAddress
                response.getTelno(),             // phone
                response.getLa(),    // latitude
                response.getLo(),    // longitude
                response.getData_stdr_de()       // standardDate
        )).collect(Collectors.toList());

        // DB에 저장
        bakeryRepository.saveAll(bakeries);
    }

    public List<BakeryResponse> getBreadList() {
        List<Bakery> bakery = bakeryRepository.findAll();
        List<BakeryResponse> bakeryRespons = bakery.stream().map(res -> BakeryResponse.from(res)).collect(Collectors.toList());
        return bakeryRespons;
    }

    public Bakery getBakery(Long id) {
        return bakeryRepository.findById(id).orElseThrow(()->new RuntimeException("존재하지 않는 베이커리입니다."));
    }


}
