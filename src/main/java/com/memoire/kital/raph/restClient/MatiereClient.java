package com.memoire.kital.raph.restClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatiereClient {
    private String id;
    private String nom;
}
