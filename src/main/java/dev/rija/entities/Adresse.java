package dev.rija.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    private Long id;
    private boolean isActive;
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;
    private Date dateEffetModification;
}
