package com.dltour.tankWar;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable, Serializable{

    @Serial
    private static final long serialVersionUID = -5805020411520602970L;
    Hero hero;
    transient Image image1;
    transient Image image2;
    transient Image image3;
    Integer destroyedTank;
    Integer score;
    Integer enemyTankSize;
    Vector<EnemyTank> enemyTanks=new Vector<>(9);
    Vector<Bomb> bombs=new Vector<>();
    transient AudioInputStream am=null;

    public MyPanel() {
        hero = new Hero(230, 450);
        hero.setColor(Color.CYAN);
        hero.setSpeed(2);
        //获取爆炸效果的图片
        try {

            image1=ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb1.png"));
            image2=ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb2.png"));
            image3=ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取开局音效
        File file=new File("E:\\project\\java\\out\\production\\java\\tankWar.wav");
        try {
            am=AudioSystem.getAudioInputStream(file);
            AudioFormat format=am.getFormat();
            DataLine.Info info=new DataLine.Info(Clip.class,format);
            Clip clip=(Clip) AudioSystem.getLine(info);
            clip.open(am);
            clip.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        enemyTankSize = 4 ;
        destroyedTank=0;
        score=0;
        hero.setTanks(new Vector<>());
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank(40*i, 40*i);
            enemyTanks.add(enemyTank);
            enemyTank.setTanks(new Vector<>());
            new Thread(enemyTank).start();
            Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 32, 2, true);
            enemyTank.bullets.add(bullet);
        }
        //将敌方坦克添加到所有的敌方坦克的集合中
        for (int j = 0; j < enemyTanks.size(); j++) {
            EnemyTank enemyTank = enemyTanks.get(j);
            //将敌方坦克添加到我方坦克集合中
            hero.getTanks().add(enemyTank);
            //将我方坦克添加到敌方坦克集合中
            enemyTank.getTanks().add(hero);
            for (int i=0;i<enemyTanks.size();i++) {
                EnemyTank enemyTank1=enemyTanks.get(i);
                //将敌方坦克添加到敌方坦克集合中
                if (enemyTank!=enemyTank1){
                    enemyTank.getTanks().add(enemyTank1);
                }
            }
            System.out.println(enemyTank.getTanks());
        }
        System.out.println(hero.getTanks());
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,500,500);
        //if hero is alive paint it
        if (hero.isAlive()) {
            paintTank(hero, g);
        }
        showInfo(g);
        for (int i=0;i<hero.bullets.size();i++){
            Bullet bullet=hero.bullets.get(i);
            if (!bullet.isAlive()){
                hero.bullets.remove(bullet);
                break;
            }
            paintBullet(bullet,g);
        }

        for (int i=0;i<bombs.size();i++) {
            Bomb bomb=bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image3, bomb.x, bomb.y, 50, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 50, 60, this);
            } else {
                g.drawImage(image1, bomb.x, bomb.y, 50, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        for (int i=0;i<enemyTanks.size();i++) {
            EnemyTank enemyTank=enemyTanks.get(i);
            if (!enemyTank.isAlive()) {
                enemyTanks.remove(enemyTank);
                continue;
            }
            paintTank(enemyTank,g);
            for (int j=0;j<enemyTank.bullets.size();j++) {
                Bullet bullet =  enemyTank.bullets.get(j);
                if (bullet.isAlive()) {
                    paintBullet(bullet, g);
                } else {
                    enemyTank.bullets.remove(bullet);
                }
            }
            }
        }

    //show the info of the tank which is destroyed by hero
    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font=new Font("宋体",Font.BOLD,20);
        g.setFont(font);
        g.drawString("消灭敌方数量:",520,30);
        g.drawString(("X:"+destroyedTank.toString()),570,100);
        g.setColor(Color.yellow);
        paintTank(new Tank(520,70),g);
    }



    public boolean hitTank(Bullet bullet,Tank enemyTank){
        switch (enemyTank.getDirection()){
            case 0:
            case 2:
                if (bullet.getX()<enemyTank.getX()+40&&bullet.getX()>enemyTank.getX()
                        &&bullet.getY()<enemyTank.getY()+50&&bullet.getY()>enemyTank.getY()){
                    bullet.setAlive(false);
                    enemyTank.setAlive(false);
                    Bomb bomb= new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                    return true;
                }
                break;
            case 1:
            case 3:
                if (bullet.getX()<enemyTank.getX()+50&&bullet.getX()>enemyTank.getX()
                        &&bullet.getY()<enemyTank.getY()+40&&bullet.getY()>enemyTank.getY()){
                    bullet.setAlive(false);
                    enemyTank.setAlive(false);
                    bombs.add(new Bomb(enemyTank.getX(),enemyTank.getY()));
                    return true;
                }
                break;
        }
        return false;
    }

    public void paintTank(Tank tank,Graphics g){
        g.setColor(tank.getColor());
        switch (tank.getDirection()){
            case 0->{
                g.fill3DRect(tank.getX(),tank.getY(),10,50,false);
                g.fill3DRect(tank.getX()+30,tank.getY(),10,50,false);
                g.fill3DRect(tank.getX()+10, tank.getY()+10,20,30 ,false);
                g.fillOval(tank.getX()+15,tank.getY()+20,10,10);
                g.drawLine(tank.getX()+20,tank.getY()+32,tank.getX()+20,tank.getY());
            }
            case 1->{
                g.fill3DRect(tank.getX(),tank.getY(),50,10,false);
                g.fill3DRect(tank.getX(),tank.getY()+30,50,10,false);
                g.fill3DRect(tank.getX()+10, tank.getY()+10,30,20 ,false);
                g.fillOval(tank.getX()+20,tank.getY()+15,10,10);
                g.drawLine(tank.getX()+18,tank.getY()+20,tank.getX()+50,tank.getY()+20);
            }
            case 2->{
                g.fill3DRect(tank.getX(),tank.getY(),10,50,false);
                g.fill3DRect(tank.getX()+30,tank.getY(),10,50,false);
                g.fill3DRect(tank.getX()+10, tank.getY()+10,20,30 ,false);
                g.fillOval(tank.getX()+15,tank.getY()+20,10,10);
                g.drawLine(tank.getX()+20,tank.getY()+18,tank.getX()+20,tank.getY()+50);
            }
            case 3->{
                g.fill3DRect(tank.getX(),tank.getY(),50,10,false);
                g.fill3DRect(tank.getX(),tank.getY()+30,50,10,false);
                g.fill3DRect(tank.getX()+10, tank.getY()+10,30,20 ,false);
                g.fillOval(tank.getX()+20,tank.getY()+15,10,10);
                g.drawLine(tank.getX()+33,tank.getY()+20,tank.getX(),tank.getY()+20);
            }
        }
    }

    public void paintBullet(Bullet bullet,Graphics g){
        g.setColor(Color.RED);
        g.draw3DRect(bullet.getX(),bullet.getY(),1,1,false);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    @SuppressWarnings("unused")
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_DOWN ){
                hero.setDirection(2);
                if (!hero.isOverlap()){
                    hero.moveDown();
                }
        }else if(e.getKeyCode()==KeyEvent.VK_UP ){
            hero.setDirection(0);
            if (!hero.isOverlap()){
                hero.moveUp();
            }
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            hero.setDirection(1);
            if (!hero.isOverlap()){
                hero.moveRight();
            }
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            hero.setDirection(3);
            if (!hero.isOverlap()){
                hero.moveLeft();
            }
        }else if(e.getKeyCode()==KeyEvent.VK_SPACE){
            String fileName="src\\gameData.dat";
            File file=new File(fileName);
            try {
              boolean result= file.exists() ? file.delete():file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(this);
                oos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("已成功存档！");
            System.out.println("您的成绩为："+score);

            System.exit(0);
        }
        else if (e.getKeyCode()==KeyEvent.VK_J){
            hero.attack();
        }else if (e.getKeyCode()==KeyEvent.VK_SHIFT){
            hero.moveUp();
        }
        this.repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i=0;i<hero.bullets.size();i++){
                if (!hero.bullets.get(i).isAlive()){
                    continue;
                }
                for (int j=0;j<enemyTanks.size();j++){
                    EnemyTank enemyTank=enemyTanks.get(j);
                    if (!enemyTank.isAlive()){
                        enemyTanks.remove(enemyTank);
                        hero.getTanks().remove(enemyTank);
                        continue;
                    }
                    if(hitTank(hero.bullets.get(i),enemyTank)){
                        score++;
                        destroyedTank++;
                        enemyTanks.remove(enemyTank);
                        hero.getTanks().remove(enemyTank);
                    }
                }
            }
            for (int i=0;i<enemyTanks.size();i++){
                EnemyTank enemyTank=enemyTanks.get(i);
                if (!enemyTank.isAlive()){
                    enemyTanks.remove(enemyTank);
                    hero.getTanks().remove(enemyTank);
                    continue;
                }
                for (int j=0;j<enemyTank.bullets.size();j++){
                    Bullet bullet= enemyTank.bullets.get(j);
                    if (hitTank(bullet,hero)){
                        System.out.println("游戏结束！");
                    }
                }
                }
            this.repaint();
        }
    }
}

