package com.spring.render.faults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Banc d'essai SmartDeploy : panne simulée.
 * L'application démarre normalement (le pipeline réussit), puis s'arrête
 * brutalement 90 secondes plus tard. Kubernetes la redémarre, et le cycle
 * recommence : le gardien doit détecter les redémarrages répétés.
 */
@Component
public class PlantageDiffere {

    private static final Logger log = LoggerFactory.getLogger(PlantageDiffere.class);

    @EventListener(ApplicationReadyEvent.class)
    public void programmerLArret() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(90_000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            log.error("Panne simulée : arrêt brutal de l'application (banc d'essai SmartDeploy)");
            Runtime.getRuntime().halt(1);
        }, "panne-plantage-differe");
        thread.setDaemon(true);
        thread.start();
    }
}
