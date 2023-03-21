package com.memoire.kital.raph.restClient;

import com.memoire.kital.raph.utils.SizeMapper;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnneeClient {
    private String id;
    private String nom;
}
