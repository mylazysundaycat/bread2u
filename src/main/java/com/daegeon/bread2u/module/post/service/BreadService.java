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
        List<Bread> breads = responseWrapper.getResponse().getBody().getItems().stream().map(response -> new Bread(
                response.getBssh_nm(),           // storeName
                response.getLnm_adrs(),          // address (지번)
                response.getRn_adrs(),           // roadAddress (도로명)
                response.getTelno(),             // phone
                response.getLa().longValue(),    // latitude
                response.getLo().longValue(),    // longitude
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

    //Todo Tistory에 정리하기
    //TODO API 자동 업데이트 배치 프로그램을 넣으려면, 어떻게 해야될까? 주기도 정해야 됨
    public List<BreadResponse> fetchApiResponses(int pageNo, int numOfRows) {
        // API URL 생성
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .build()
                .toUriString();

        // API 호출 및 JSON 응답 매핑
        ApiResponseWrapper responseWrapper = restTemplate.getForObject(url, ApiResponseWrapper.class);
        if (responseWrapper == null || responseWrapper.getResponse() == null || responseWrapper.getResponse().getBody() == null) {
            throw new RuntimeException("API 호출 실패 또는 응답 데이터가 없습니다.");
        }

        // body -> items 리스트 가져오기
        List<ApiResponse> apiResponses = responseWrapper.getResponse().getBody().getItems();
        System.out.println("API Response Items: " + apiResponses);

        // ApiResponse -> Bread 엔티티로 변환
        List<Bread> breads = responseWrapper.getResponse().getBody().getItems().stream().map(response -> new Bread(
                response.getBssh_nm(),           // storeName
                response.getLnm_adrs(),          // address (지번)
                response.getRn_adrs(),           // roadAddress (도로명)
                response.getTelno(),             // phone
                response.getLa().longValue(),    // latitude
                response.getLo().longValue(),    // longitude
                response.getData_stdr_de()       // standardDate
        )).collect(Collectors.toList());

        // DB에 저장
        breadRepository.saveAll(breads);

        //Bread -> BreadResponse
        List<BreadResponse> breadResponses = breads.stream()
                .map(response -> BreadResponse.from(response))
                .collect(Collectors.toList());

        return breadResponses;

    }



}
