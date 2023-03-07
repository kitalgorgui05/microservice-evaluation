package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.feignRestClient.AnneeRestClient;
import com.memoire.kital.raph.restClient.AnneeClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnneeClientService {
    private final AnneeRestClient anneeRestClient;

    public AnneeClientService(AnneeRestClient anneeRestClient) {
        this.anneeRestClient = anneeRestClient;
    }
    public List<AnneeClient> getAllAnneeClient(){
        return anneeRestClient.getAllAnnees().getBody();
    }
}
