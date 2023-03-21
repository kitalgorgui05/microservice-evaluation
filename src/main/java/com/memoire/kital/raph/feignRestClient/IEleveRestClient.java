package com.memoire.kital.raph.feignRestClient;

import com.memoire.kital.raph.restClient.EleveDTOReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}", url = "http://localhost:8888")
public interface IEleveRestClient {
    @GetMapping("/api/eleves/inscriptions")
    List<EleveDTOReq> getAllElevesInscrit();
    @GetMapping("/api/eleves/{id}")
    ResponseEntity<EleveDTOReq> getEleve(@PathVariable("id") String id);
}
