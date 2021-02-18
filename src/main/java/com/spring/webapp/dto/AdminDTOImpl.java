package com.spring.webapp.dto;

public class AdminDTOImpl extends AllDTOUser {
    public AdminDTOImpl() {
        super();
    }
    public AdminDTOImpl(int id, String name, String surname, String position, String username) {
        super(id,name,surname,position,username);
    }
}

