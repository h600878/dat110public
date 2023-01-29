package no.hvl.dat110.threading;

import java.util.Random;

public class TemperatureSensor {

    private final Random random;

    private static final int RANGE = 25;

    public TemperatureSensor() {
        random = new Random();
    }

    public int read() {
        return random.nextInt(RANGE * 2 + 1) - RANGE;
    }
}
