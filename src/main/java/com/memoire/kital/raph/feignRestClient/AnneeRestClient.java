package com.memoire.kital.raph.feignRestClient;

import com.memoire.kital.raph.feignRestClient.interceptor.FeignClientInterceptor;
import com.memoire.kital.raph.restClient.AnneeClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}", url = "http://localhost:8888")
public interface AnneeRestClient {
    @GetMapping("/api/annees/{id}")
    ResponseEntity<AnneeClient> getAnnee(@PathVariable("id") String id);
}
