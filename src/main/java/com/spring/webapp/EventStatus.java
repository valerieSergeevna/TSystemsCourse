package com.spring.webapp;

public enum EventStatus implements StatusEnum {

    IN_PLAN("in plan"),
    CANCELED("canceled"),
    COMPLETED("completed");

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    EventStatus(String title) {
        this.title = title;
    }


    public static EventStatus fromTitle(String title) {
        switch (title) {
            case "in plan":
                return IN_PLAN;
            case "canceled":
                return CANCELED;
            case "completed":
                return COMPLETED;
            default:
                throw new IllegalArgumentException("ShortName [" + title
                        + "] not supported.");
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
