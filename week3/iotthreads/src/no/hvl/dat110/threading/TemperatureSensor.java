package no.hvl.dat110.threading;

import java.util.Random;

public class TemperatureSensor {

    private static final int RANGE = 25;

    public int read() {
        return new Random().nextInt(-RANGE, RANGE);
    }
}
