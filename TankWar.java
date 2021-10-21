package com.dltour.tankWar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


public class TankWar extends JFrame {
    public TankWar(){
        MyPanel mp=null;
        Object[] options={"新游戏","继续游戏"};
        int response=JOptionPane.showOptionDialog(this,"欢迎来到坦克世界",
                "坦克世界",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (response==0){
            System.out.println("您选择了新游戏!");
            mp=new MyPanel();
            mp.setSize(500,500);
        }else {
            System.out.println("您选择了继续游戏!");
            File file=new File("src\\gameData.dat");
            if (!file.exists()) {
                System.out.println("抱歉，您没有存档游戏，已经为您重新开始新游戏！");
                mp=new MyPanel();
                mp.setSize(500,500);
            }else {
                try {
                    ObjectInputStream ois=new ObjectInputStream(new FileInputStream("e:\\gameData.dat"));
                    mp=(MyPanel) ois.readObject();
                    mp.setSize(500,500);
                    try {
                        mp.image1= ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb1.png"));
                        mp.image2= ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb2.png"));
                        mp.image3= ImageIO.read(new File("E:\\project\\java\\out\\production\\java\\bomb3.png"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<mp.enemyTanks.size();i++){
                        new Thread(mp.enemyTanks.get(i)).start();
                        for (int j=0;j<mp.enemyTanks.get(i).bullets.size();j++){
                            new Thread(mp.enemyTanks.get(i).bullets.get(j)).start();
                        }
                    }
                    for (int i=0;i<mp.hero.bullets.size();i++) {
                        new Thread(mp.hero.bullets.get(i)).start();
                    }
                    System.out.println("成功读取文件,已经为您继续游戏！");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        this.add(mp);
        new Thread(mp).start();
        this.setSize(700,700);
        this.setTitle("坦克大战1.0");
        this.addKeyListener(mp);//JFrame must add the key Listener();
        this.setVisible(true);
        MyPanel finalMp = mp;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                String fileName="src\\gameData.dat";
                File file=new File(fileName);
                try {
                    boolean result= file.exists() ? file.delete():file.createNewFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(finalMp);
                    oos.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("已成功存档！");
                System.out.println("您的成绩为："+ finalMp.score);
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new TankWar();
    }


}
