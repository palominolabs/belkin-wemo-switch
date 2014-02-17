package com.palominolabs.wemo;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Device;

public class InsightSwitch {
    private final Device device;

    public InsightSwitch(Device device) {
        this.device = device;
    }

    public PowerUsage getPowerUsage() throws Exception {
        Action action = device.getAction("GetInsightParams");
        performAction(action);

        return new PowerUsage(action.getArgumentValue("InsightParams"));
    }

    public void switchOn() throws InsightSwitchOperationException {
        setSwitchIsOn(true);
    }

    public void switchOff() throws InsightSwitchOperationException {
        setSwitchIsOn(false);
    }

    public void setSwitchIsOn(boolean on) throws InsightSwitchOperationException {
        Action action = device.getAction("SetBinaryState");
        action.setArgumentValue("BinaryState", on ? 1 : 0);

        performAction(action);
    }

    public String getFriendlyName() throws InsightSwitchOperationException {
        Action action = device.getAction("GetFriendlyName");
        performAction(action);
        return action.getArgumentValue("FriendlyName");
    }

    private void performAction(Action action) throws InsightSwitchOperationException {
        if (!action.postControlAction()) {
            throw new InsightSwitchOperationException(action.getStatus());
        }
    }

}
