package no.hvl.dat110.threading;

import java.util.List;

public class IoTSystem {

    public static void main(String[] args) {

        System.out.println("System starting ... ");

        TemperatureMeasurement tm = new TemperatureMeasurement();

        List<TemperatureDevice> temperatureDevices = List.of(new TemperatureDevice(tm), new TemperatureDevice(tm));
        List<DisplayDevice> displaydevices = List.of(new DisplayDevice(tm), new DisplayDevice(tm), new DisplayDevice(tm));

        displaydevices.forEach(d -> d.setDaemon(true));

        temperatureDevices.forEach(Thread::start);
        displaydevices.forEach(Thread::start);

        try {
            for (TemperatureDevice temperatureDevice : temperatureDevices) {
                temperatureDevice.join();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("System shutting down ... ");

    }

}
