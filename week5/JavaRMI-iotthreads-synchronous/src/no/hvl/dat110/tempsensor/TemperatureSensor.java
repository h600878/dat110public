package no.hvl.dat110.tempsensor;

import java.util.Random;

public class TemperatureSensor {

    private static final Random random = new Random();

    public int read() {
        return random.nextInt(40);
    }
}
