package com.spring.webapp.dto;

public abstract class AbstractDTOUser implements UserDTO{
    private int id;//????
    private String name;
    private String surname;
    private String position;
    private String username;

    public AbstractDTOUser() {
    }

    public AbstractDTOUser(int id, String name, String surname, String position, String username) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.username = username;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
