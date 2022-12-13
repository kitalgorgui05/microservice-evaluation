package com.memoire.kital.raph.feignRestClient;

import com.memoire.kital.raph.restClient.ClasseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "classe", url = "http://localhost:8086")
public interface ClasseRestClient {

    @GetMapping("/api/classes")
    public ResponseEntity<List<ClasseClient>> getAllClasses();

    @GetMapping("/api/classes/{id}")
    public ResponseEntity<ClasseClient> getClasse(@PathVariable("id") String id);
}
