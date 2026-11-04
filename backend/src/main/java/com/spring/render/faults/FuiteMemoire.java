package com.spring.render.faults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Banc d'essai SmartDeploy : panne simulée.
 * Après 60 secondes de fonctionnement normal, l'application retient 10 Mo
 * toutes les 2 secondes sans jamais les libérer. Avec -Xmx256m et
 * -XX:+ExitOnOutOfMemoryError (Dockerfile), elle s'arrête une fois la
 * mémoire épuisée ; Kubernetes la redémarre et la fuite recommence.
 */
@Component
public class FuiteMemoire {

    private static final Logger log = LoggerFactory.getLogger(FuiteMemoire.class);
    private static final List<byte[]> RETENU = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void demarrerLaFuite() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(60_000);
                log.warn("Panne simulée : début de la fuite mémoire (banc d'essai SmartDeploy)");
                while (true) {
                    RETENU.add(new byte[10 * 1024 * 1024]);
                    Thread.sleep(2_000);
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }, "panne-fuite-memoire");
        thread.setDaemon(true);
        thread.start();
    }
}
