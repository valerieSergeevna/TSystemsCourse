package com.spring.webapp;


public enum PatientStatus implements StatusEnum{
    IN_PROCESS("in process"),
    DISCHARGED("discharged");

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    PatientStatus(String title) {
        this.title = title;
    }


    public static PatientStatus fromTitle(String title) {
        switch (title) {
            case "in process":
                return PatientStatus.IN_PROCESS;

            case "discharged":
                return PatientStatus.DISCHARGED;
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
