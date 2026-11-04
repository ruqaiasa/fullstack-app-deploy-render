package com.spring.render.faults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Banc d'essai SmartDeploy : panne simulée.
 * Après 60 secondes, deux boucles actives consomment le processeur en
 * permanence. L'application reste disponible : le gardien doit émettre un
 * avertissement (dérive CPU), sans déclarer de dégradation critique.
 */
@Component
public class CpuIntensif {

    private static final Logger log = LoggerFactory.getLogger(CpuIntensif.class);
    private static volatile long compteur;

    @EventListener(ApplicationReadyEvent.class)
    public void demarrerLesBoucles() {
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(60_000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    return;
                }
                log.warn("Panne simulée : boucle active sur le processeur (banc d'essai SmartDeploy)");
                while (!Thread.currentThread().isInterrupted()) {
                    compteur++;
                }
            }, "panne-cpu-" + i);
            thread.setDaemon(true);
            thread.start();
        }
    }
}
