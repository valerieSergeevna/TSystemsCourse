package com.spring.webapp.dto;

public class NurseDTOImpl extends AllDTOUser {
    public NurseDTOImpl() {
        super();
    }
    public NurseDTOImpl(int id, String name, String surname, String position, String username) {
        super(id,name,surname,position,username);
    }
}

