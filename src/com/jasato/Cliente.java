package com.jasato;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Cliente implements Runnable {

    private final Supermercado supermercado;

    public Cliente (Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    @Override
    public void run() {
        try {
            comprar();
            supermercado.pasarPorCaja();
        } catch (InterruptedException e) {
            return;
        }


    }

    public void comprar() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
    }
}
