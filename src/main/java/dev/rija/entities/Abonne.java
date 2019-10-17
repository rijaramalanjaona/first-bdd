package dev.rija.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Abonne {
    private Long id;
    private Adresse adressePrincipale;
    private List<Contrat> contrats = new ArrayList<>();
    private List<Archive> archives = new ArrayList<>();
}
