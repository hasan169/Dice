package com.entity;

public class Player {
    private String id;
    private String name;
    private int age;
    private Integer score;

    public Player(String id, String name, int age, Integer score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public Player(String name, int age) {
        this(null, name, age, null);
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Player name: %s, Player id: %s, Player age: %s, Score: %s", name, id, age, score);
    }
}
