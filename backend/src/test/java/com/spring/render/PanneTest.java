package com.spring.render;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Banc d'essai SmartDeploy : test unitaire en échec volontaire (sans base de données). */
class PanneTest {

    @Test
    void calculDeLaTva() {
        double prixHt = 100.0;
        double prixTtc = prixHt * 1.19;
        assertEquals(120.0, prixTtc, 0.001, "Panne simulée : le taux appliqué est erroné");
    }
}
