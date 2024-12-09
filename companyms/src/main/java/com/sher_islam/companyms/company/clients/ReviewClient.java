package com.sher_islam.companyms.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="REVIEW-SERVICE",
        url="${company-service.url}")
public interface ReviewClient {
    @GetMapping("/reviews/averageRating")
    Double getAverageRatingForCompany(@RequestParam("companyId")
                                      Long companyId);
}
