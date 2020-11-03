package com.jasato;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Supermercado {

    private int numeroCajas;
    private boolean[] cajas;
    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock(true);

    public Supermercado(int numeroCajas) {
        this.numeroCajas = numeroCajas;
        cajas = new boolean[numeroCajas];
        for (int i = 0; i < numeroCajas; i++) cajas[i] = true;
        semaphore = new Semaphore(numeroCajas);
    }



    public void pasarPorCaja() throws InterruptedException {
        semaphore.acquire();
        try {
            int caja = seleccionarCaja();
            if (caja >= 0) {
                pagar(caja);
                abandonarCaja(caja);
            }
        } finally {
            semaphore.release();

        }

    }

    public void pagar(int caja) throws InterruptedException {
        System.out.printf("%s está pagando en la caja %d\n", Thread.currentThread().getName(), caja);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4)+1);

    }

    private void abandonarCaja(int caja) {
        lock.lock();
        try {
            cajas[caja] = true;
            System.out.printf("%s ha abandonado  la caja %d\n", Thread.currentThread().getName(), caja);
        } finally {
            lock.unlock();
        }
    }

    private int seleccionarCaja() {
        lock.lock();
        try {
            for (int i = 0; i < numeroCajas; i++) {
                if (cajas[i]) {
                    cajas[i] = false;
                    System.out.printf("%s pasa por la caja %d\n", Thread.currentThread().getName(), i);

                    return i;
                }
            }
            return -1;

        } finally {
            lock.unlock();

        }
    }
}
