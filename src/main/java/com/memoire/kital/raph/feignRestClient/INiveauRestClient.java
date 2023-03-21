package com.memoire.kital.raph.feignRestClient;

import com.memoire.kital.raph.restClient.NiveauClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}", url = "http://localhost:8802")
public interface INiveauRestClient {
    @GetMapping("/niveaus")
    ResponseEntity<List<NiveauClient>> getAllNiveaus();
}
