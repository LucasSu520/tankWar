package com.dltour.tankWar;

import java.util.Vector;

public class Hero extends Tank{

    Vector<Bullet> bullets=new Vector<>();

    public Hero(int x, int y) {
        super(x,y);
    }

    @Override
    public void attack() {
        //limit the maximum of the bullet
        if (this.bullets.size()==5){
            return;
        }
        switch (this.getDirection()){
            case 0:
                Bullet bullet = new Bullet(getX() + 20, getY(), getDirection(), true);
                new Thread(bullet).start();
                bullets.add(bullet);
                break;
            case 1:
                Bullet bullet1= new Bullet(getX()+50,getY()+20,getDirection(),true);
                new Thread(bullet1).start();
                bullets.add(bullet1);
                break;
            case 2:
                Bullet bullet2 = new Bullet(getX() + 20, getY() + 50, getDirection(), true);
                new Thread(bullet2).start();
                bullets.add(bullet2);
                break;
            case 3:
                Bullet bullet3 = new Bullet(getX(), getY() + 20, getDirection(), true);
                new Thread(bullet3).start();
                bullets.add(bullet3);
                break;
            default:
                Bullet bullet4 = new Bullet(getX(), getY(), getDirection(), true);
                new Thread(bullet4).start();
                bullets.add(bullet4);
                break;
        }

    }
}
