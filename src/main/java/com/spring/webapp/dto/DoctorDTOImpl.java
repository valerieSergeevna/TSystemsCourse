package com.spring.webapp.dto;

public class DoctorDTOImpl extends AllDTOUser {
    public DoctorDTOImpl() {
        super();
    }

    public DoctorDTOImpl(int id,String name, String surname, String position, String username) {
        super(id,name,surname,position,username);
    }
}

