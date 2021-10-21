package com.dltour.tankWar;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;


public class Tank implements Serializable {
    private int x;
    private int y;
    private int speed;
    private int direction;
    private Color color;
    private Bullet bullet;
    private boolean isAlive=true;
    private Vector<Tank> tanks;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, int direction , Color color, int speed, Bullet bullet) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color=color;
        this.speed=speed;
        this.bullet=bullet;
    }

    public void attack(){
        switch (getDirection()){
            case 0:
                bullet=new Bullet(getX()+20,getY()+32,getDirection(),true);
            case 1:
                bullet=new Bullet(getX()+18,getY()+20,getDirection(),true);
            case 2:
                bullet=new Bullet(getX()+20,getY()+18,getDirection(),true);
            case 3:
                bullet=new Bullet(getX()+33,getY()+20,getDirection(),true);
            default:
                bullet=new Bullet(getX(),getY(),getDirection(),true);
        }
        new Thread(bullet).start();
    }

    //判断是否与其他的坦克重叠；
    public boolean isOverlap(){
        switch (this.getDirection()){
            case 0:
                for (int i=0;i<tanks.size();i++) {
                    Tank tank = tanks.get(i);
                    if (this!=tank) {
                        if (tank.direction == 0 || tank.direction == 2) {
                            if (this.getX()> tank.getX()
                                    && this.getX() < tank.getX() + 40
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 50) {
                                return true;
                            }
                            if (this.getX()+40> tank.getX()
                                    && this.getX()+40< tank.getX() + 40
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 50) {
                                return true;
                            }
                        }
                        if (tank.direction==1||tank.direction==3){
                            if (this.getX() > tank.getX()
                                    && this.getX() < tank.getX() + 50
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 40) {
                                return true;
                            }
                            if (this.getX()+40 >= tank.getX()
                                    && this.getX()+40 < tank.getX() + 50
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int i=0;i<tanks.size();i++) {
                    Tank tank = tanks.get(i);
                    if (this!=tank) {
                        if (tank.direction == 0 || tank.direction == 2) {
                            if (this.getX() > tank.getX()
                                    && this.getX() < tank.getX() + 40
                                    && this.getY()+50 > tank.getY()
                                    && this.getY()+50< tank.getY() + 50) {
                                return true;
                            }
                            if (this.getX()+40 > tank.getX()
                                    && this.getX()+40< tank.getX() + 40
                                    && this.getY()+50 > tank.getY()
                                    && this.getY()+50< tank.getY() + 50) {
                                return true;
                            }
                        }
                        if (tank.direction==1||tank.direction==3){
                            if (this.getX()> tank.getX()
                                    && this.getX()< tank.getX() + 50
                                    && this.getY()+50 > tank.getY()
                                    && this.getY()+50< tank.getY() + 40) {
                                return true;
                            }
                            if (this.getX()+40> tank.getX()
                                    && this.getX()+40< tank.getX() + 50
                                    && this.getY()+50 > tank.getY()
                                    && this.getY()+50< tank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1:
                for (int i=0;i<tanks.size();i++) {
                    Tank tank = tanks.get(i);
                    if (this!=tank) {
                        if (tank.direction == 0 || tank.direction == 2) {
                            if (this.getX()+50> tank.getX()
                                    && this.getX()+50< tank.getX() + 40
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 50) {
                                return true;
                            }
                            if (this.getX()+50> tank.getX()
                                    && this.getX()+50< tank.getX() + 40
                                    && this.getY()+40> tank.getY()
                                    && this.getY()+40< tank.getY() + 50) {
                                return true;
                            }
                        }
                        if (tank.direction==1||tank.direction==3) {
                            if (this.getX()+50 > tank.getX()
                                    && this.getX()+50< tank.getX() +50
                                    && this.getY() > tank.getY()
                                    && this.getY()< tank.getY() + 40) {
                                return true;
                            }
                            if (this.getX()+50 > tank.getX()
                                    && this.getX()+50< tank.getX() +50
                                    && this.getY()+40> tank.getY()
                                    && this.getY()+40< tank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }

                break;
            case 3:
                for (int i=0;i<tanks.size();i++) {
                    Tank tank = tanks.get(i);
                    if (this!=tank) {
                        if (tank.direction == 0 || tank.direction == 2) {
                            if (this.getX() > tank.getX()
                                    && this.getX() < tank.getX() + 40
                                    && this.getY() > tank.getY()
                                    && this.getY() < tank.getY() + 50) {
                                return true;
                            }
                            if (this.getX() > tank.getX()
                                    && this.getX() < tank.getX() + 40
                                    && this.getY()+40> tank.getY()
                                    && this.getY()+40< tank.getY() + 50) {
                                return true;
                            }
                        }
                        if (tank.direction==1||tank.direction==3) {
                            if (this.getX() >tank.getX()
                                    && this.getX() < tank.getX() + 50
                                    && this.getY() > tank.getY()
                                    && this.getY() <tank.getY() + 40) {
                                return true;
                            }
                            if (this.getX() > tank.getX()
                                    && this.getX() < tank.getX() + 50
                                    && this.getY()+40> tank.getY()
                                    && this.getY()+40<  tank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }

        return false;
    }

    public void moveUp(){
        if(this.direction!=0){
            this.direction=0;
        }
        if (this.y>0) {
            this.y -= speed;
        }else if (this.y<=0){
            this.y=0;
        }
    }

    public void moveDown(){
        if(this.direction!=2){
            this.direction=2;
        }
        if (this.y<450) {
            this.y += speed;
        }else if (this.y>=450){
            this.y=450;
        }
    }


    public void moveLeft(){
        if(this.direction!=3){
            this.direction=3;
        }
        if (this.x>0){
            this.x-=speed;
        }else if (this.x<=0){
            this.x=0;
        }
    }

    public void moveRight(){
        if(this.direction!=1){
            this.direction=1;
        }
        if (this.x<450) {
            this.x += speed;
        }else if (this.x>=450){
            this.x=450;
        }
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public Vector<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(Vector<Tank> tanks) {
        this.tanks = tanks;
    }
}

class Bullet implements Runnable,Serializable{
    private int x;
    private int y;
    private int direction;
    private boolean isAlive;

    public Bullet(int x, int y, int direction,boolean isAlive) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isAlive=isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }



    @Override
    public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (this.getDirection()) {
                    case 0:
                        y -= 2;
                        break;
                    case 1:
                        x += 2;
                        break;
                    case 2:
                        y += 2;
                        break;
                    case 3:
                        x -= 2;
                        break;
                }
                if (!(x < 500 && y < 500 && x > 0 && y > 0 && this.isAlive())) {
                    this.isAlive = false;
                    return;
                }
            }

    }




    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
