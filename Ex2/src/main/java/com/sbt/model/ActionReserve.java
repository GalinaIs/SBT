package com.sbt.model;

public class ActionReserve {
    private boolean allowedReserve;
    private boolean cancelReserve;

    public boolean isAllowedReserve() {
        return allowedReserve;
    }

    public void setAllowedReserve(boolean allowedReserve) {
        this.allowedReserve = allowedReserve;
    }

    public boolean isCancelReserve() {
        return cancelReserve;
    }

    public void setCancelReserve(boolean cancelReserve) {
        this.cancelReserve = cancelReserve;
    }
}
