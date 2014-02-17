package com.palominolabs.wemo;

public class PowerUsage {
    public enum State {ON, OFF, STANDBY}

    private final State currentState;
    private final int stateTransitionTimestamp;
    private final int lastOnSeconds;
    private final int onTodaySeconds;
    private final int currentMilliWatts;

    PowerUsage(String insightPowerUsage) {
        String[] components = insightPowerUsage.split("\\|");

        if (components.length != 11) {
            throw new IllegalArgumentException("Invalid power usage string: <" + insightPowerUsage + ">");
        }

        switch (Integer.parseInt(components[0])) {
            case 0:
                currentState = State.OFF;
                break;
            case 1:
                currentState = State.ON;
                break;
            case 8:
                currentState = State.STANDBY;
                break;
            default:
                throw new IllegalArgumentException("Invalid state: " + components[0]);
        }

        stateTransitionTimestamp = Integer.parseInt(components[1]);
        lastOnSeconds = Integer.parseInt(components[2]);
        onTodaySeconds = Integer.parseInt(components[3]);
        currentMilliWatts = Integer.parseInt(components[7]);
    }

    public State getCurrentState() {
        return currentState;
    }

    public int getStateTransitionTimestamp() {
        return stateTransitionTimestamp;
    }

    public int getLastOnSeconds() {
        return lastOnSeconds;
    }

    public int getOnTodaySeconds() {
        return onTodaySeconds;
    }

    public int getCurrentMilliWatts() {
        return currentMilliWatts;
    }
}
