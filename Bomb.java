package com.dltour.tankWar;

public class Bomb {
    int x;
    int y;
    int life=9;
    boolean isAlive=true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown(){
        if (life>0){
            life--;
        }else {
            this.isAlive=false;
        }
    }
}
