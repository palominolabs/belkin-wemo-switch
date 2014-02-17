package com.palominolabs.wemo;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.DeviceChangeListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class InsightSwitchFinder implements AutoCloseable {

    private static final String BELKIN_INSIGHT_DEVICE_TYPE = "urn:Belkin:device:insight:1";

    private final ControlPoint controlPoint;

    private final List<String> friendlyNames;

    public InsightSwitchFinder(String... friendlyNames) {
        if (friendlyNames.length == 0) {
            throw new IllegalArgumentException("Specify at least 1 friendly name to search for");
        }

        this.friendlyNames = Collections.unmodifiableList(Arrays.asList(friendlyNames));

        this.controlPoint = new ControlPoint();
        if (!controlPoint.start()) {
            throw new RuntimeException("Unable to start UPnP ControlPoint");
        }
    }

    public boolean findSwitches() throws InterruptedException {
        return findSwitches(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public boolean findSwitches(long timeout, TimeUnit unit) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(friendlyNames.size());


        controlPoint.addDeviceChangeListener(new DeviceChangeListener() {
            @Override
            public void deviceAdded(Device device) {
                if (!device.getDeviceType().equals(BELKIN_INSIGHT_DEVICE_TYPE)) {
                    return;
                }

                InsightSwitch insightSwitch = new InsightSwitch(device);


                try {
                    if (friendlyNames.contains(insightSwitch.getFriendlyName())) {
                        countDownLatch.countDown();
                    }
                } catch (InsightSwitchOperationException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void deviceRemoved(Device device) {

            }
        });

        return countDownLatch.await(timeout, unit);
    }

    public InsightSwitch getSwitch(String friendlyName) throws InsightSwitchNotFoundException {
        Device device = controlPoint.getDevice(friendlyName);

        if (device == null) {
            throw new InsightSwitchNotFoundException("Device named <" + friendlyName + "> not found. Make sure you passed the name to the constructor and called findSwitches()");
        }

        return new InsightSwitch(device);
    }

    @Override
    public void close() throws Exception {
        controlPoint.stop();
    }
}
