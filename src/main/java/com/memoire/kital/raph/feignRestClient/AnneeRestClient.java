package com.memoire.kital.raph.feignRestClient;

import com.memoire.kital.raph.restClient.AnneeClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "inscription", url = "http://localhost:9081")
public interface AnneeRestClient {
    @GetMapping("/annees")
    ResponseEntity<List<AnneeClient>> getAllAnnees();
    @GetMapping("/api/annees/{id}")
    ResponseEntity<AnneeClient> getAnnee(@PathVariable("id") String id);
}
