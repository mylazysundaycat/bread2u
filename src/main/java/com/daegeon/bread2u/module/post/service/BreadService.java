package com.daegeon.bread2u.module.post.service;


import com.daegeon.bread2u.module.post.dto.BreadRequest;
import com.daegeon.bread2u.module.post.dto.BreadResponse;
import com.daegeon.bread2u.module.post.entity.Bread;
import com.daegeon.bread2u.module.post.repository.BreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BreadService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String ApiUri = "https://www.seogu.go.kr/seoguAPI/3660000/getBakryStts";
    private final BreadRepository breadRepository;

    public BreadResponse createBread(BreadRequest breadRequest){
        Bread bread = new Bread(
                breadRequest.getStoreName(),
                breadRequest.getAddress(),
                breadRequest.getRoadAddress(),
                breadRequest.getPhone(),
                breadRequest.getLatitude(),
                breadRequest.getLongitude(),
                breadRequest.getStandardDate()
        );

//        breadRepository.save();
    }


}
