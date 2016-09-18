package com.home.beans;

import com.home.model.StatusType;

/**
 * Created by dmitry on 9/17/16.
 */
public class Status {
    private StatusType statusType;

    public Status() {
        statusType = StatusType.failed;
    }

    public Status(StatusType statusType) {
        this.statusType = statusType;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
