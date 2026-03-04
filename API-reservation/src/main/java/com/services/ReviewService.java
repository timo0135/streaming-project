package com.services;


import com.dtos.PaymentDto;
import com.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ReviewService {

    @Value("${review.api.url}")
    private String reviewApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // GET /reviews
    public List<ReviewDto> getAllReviews() {
        ReviewDto[] reviews = restTemplate.getForObject(
                reviewApiUrl + "/reviews", ReviewDto[].class);
        assert reviews != null;
        return Arrays.asList(reviews);
    }

    // GET /reviews/{id}
    public ReviewDto getReviewById(int id) {
        ReviewDto rev = new ReviewDto();
        rev.setId((long)1);
        rev.setCommentaire("Bien");
        rev.setNote(5);
        return rev;
        //return restTemplate.getForObject( reviewApiUrl + "/reviews/" + id, ReviewDto.class);
    }
}
