package com.dltour.tankWar;

import java.awt.*;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {

    Vector<Bullet> bullets = new Vector<>();


    public EnemyTank() {
        super(50, 0, 2, Color.YELLOW, 3, new Bullet(50, 0, 2, false));
    }

    public EnemyTank(int x, int y) {
        super(x, y, 2, Color.YELLOW, 3, new Bullet(50, 0, 2, false));
    }

    public EnemyTank(int x, int y, int direction, Color color, int speed, Bullet bullet) {
        super(x, y, direction, color, speed, bullet);
    }

    @Override
    public void attack() {
        switch (this.getDirection()) {
            case 0:
                Bullet bullet = new Bullet(getX() + 20, getY(), getDirection(), true);
                new Thread(bullet).start();
                bullets.add(bullet);
                break;
            case 1:
                Bullet bullet1 = new Bullet(getX() + 50, getY() + 20, getDirection(), true);
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

    //let enemyTank move randomly
    @Override
    public void run() {
        while (true) {
            switch (getDirection()) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if (!this.isOverlap()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                        case 1:
                            for (int i = 0; i < 30; i++) {
                                if (!this.isOverlap()) {
                                    moveRight();
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 2:
                            for (int i = 0; i < 30; i++) {
                                if (!this.isOverlap()) {
                                    moveDown();
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 3:
                            for (int i = 0; i < 30; i++) {
                                if (!this.isOverlap()) {
                                    moveLeft();
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            break;
                    }
                    setDirection((int) (Math.random() * 4));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.attack();
                    if (!isAlive()) {
                        break;
                    }
            }
        }
    }
