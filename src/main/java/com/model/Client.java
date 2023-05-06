package com.model;

import java.time.LocalDate;

public class Client {

    private Long id;
    private String name;
    private int age;
    private int groupId;
    private String phone;
    private LocalDate date;

    public Client(String name, int age, int groupId, String phone, LocalDate date) {
        this.name = name;
        this.age = age;
        this.groupId = groupId;
        this.phone = phone;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
