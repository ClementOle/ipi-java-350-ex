package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class EmployeServiceIntegTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @AfterEach
    @BeforeEach
    public void cleanUp(){
        employeRepository.deleteAll();
    }

    @Test
    void testEmbauchePremierEmployePleinTemps() throws Exception {
        //Given
        String matricule = "M00001";
        String nom = "NOM";
        String prenom = "PRENOM";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.INGENIEUR;
        Double tempsPartiel = 1d;
        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //Then
        Employe employe = employeRepository.findByMatricule(matricule);

        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getMatricule()).isEqualTo(matricule);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2433.952d);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
    }

    @Test
    void testIntegrationCalculPerformanceCommercial() throws EmployeException {
        //Given
        String matricule = "C24355";
        Employe e = new Employe();
        e.setMatricule(matricule);
        e.setPerformance(4);
        employeRepository.save(e);
        Long objectifCa = 42000L;
        Long caTraite = 37800L;


        //When
        employeService.calculPerformanceCommercial(e.getMatricule(), caTraite, objectifCa);

        //Then
        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(2);
    }
}
