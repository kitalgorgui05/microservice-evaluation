package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.service.impl.AnneeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api")
public class AnneeClientRessource {
    private final AnneeClientService anneeClientService;

    public AnneeClientRessource(AnneeClientService anneeClientService) {
        this.anneeClientService = anneeClientService;
    }

    @GetMapping( "/annees")
    public List<AnneeClient> getAll(){
        return anneeClientService.getAllAnneeClient();
    }
}
