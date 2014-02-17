package com.palominolabs.wemo;

import org.cybergarage.upnp.UPnPStatus;

public class InsightSwitchOperationException extends Exception {
    private final UPnPStatus status;

    public InsightSwitchOperationException(UPnPStatus status) {
        super("Insight switch returned error: " + status.getCode() + " " + status.getDescription());
        this.status = status;
    }

    public UPnPStatus getStatus() {
        return status;
    }
}
