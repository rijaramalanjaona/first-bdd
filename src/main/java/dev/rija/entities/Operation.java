package dev.rija.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private Long id;
    private String canalConnexion;
    private boolean sansDateEffet;
    private Abonne abonneConcerne;
    private String nom;
    private Date date;
}
