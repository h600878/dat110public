package no.hvl.dat110.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class TemperatureMeasurement {

    private final AtomicInteger temp;

    public TemperatureMeasurement() {
        temp = new AtomicInteger(0);
    }

    public int getTemperature() {
        return temp.get();
    }

    public void setTemperature(int temp) {
        this.temp.set(temp);
    }
}
