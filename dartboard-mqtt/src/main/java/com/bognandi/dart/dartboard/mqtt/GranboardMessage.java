package com.bognandi.dart.dartboard.mqtt;

import com.bognandi.dart.core.dartboard.DartboardValue;
import com.bognandi.dart.core.dartboard.DartboardStatus;

public class GranboardMessage {
    private DartboardStatus status;
    private DartboardValue value;

    public GranboardMessage() {
    }

    public GranboardMessage(DartboardStatus status, DartboardValue value) {
        this.status = status;
        this.value = value;
    }

    public DartboardStatus getStatus() {
        return status;
    }

    public void setStatus(DartboardStatus status) {
        this.status = status;
    }

    public DartboardValue getValue() {
        return value;
    }

    public void setValue(DartboardValue value) {
        this.value = value;
    }
}
