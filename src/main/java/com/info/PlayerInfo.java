package com.info;

public class PlayerInfo {

    private String id;
    private String name;
    private int age;

    public PlayerInfo(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public PlayerInfo(String name, int age) {
        this(null, name, age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
