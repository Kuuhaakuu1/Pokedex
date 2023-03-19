package com.kuuhaakuu.pokedex;

public class Pokemon {
    private String spriteLink;
    private int hp;
    private int defense;
    private int attack;
    private String name;

    public Pokemon(String spriteLink, int hp, int defense, int attack, String name) {
        this.spriteLink = spriteLink;
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.name = name;
    }

    public String getSpriteLink() {
        return spriteLink;
    }

    public int getHp() {
        return hp;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }
}

