import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class MainPanel extends JPanel implements KeyListener {
    int time = 0;
    BufferedImage image1;
    BufferedImage image2;
    HockeyBar hockeyBar1 = new HockeyBar(50);
    HockeyBar hockeyBar2 = new HockeyBar(450);
    HockeyBall hockeyBall = new HockeyBall();
    JLabel redPointLabel;
    JLabel bluePointLabel;

    int status = 0;
    int redPoint = 0;
    int bluePoint = 0;
    boolean isLEFT1  = false;
    boolean isRIGHT1 = false;
    boolean isLEFT2  = false;
    boolean isRIGHT2 = false;

    public MainPanel(JLabel redPointLabel, JLabel bluePointLabel){
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.setVisible(true);
        this.addKeyListener((KeyListener) this);
        this.setPreferredSize(new Dimension(1000, 500));
        this.redPointLabel = redPointLabel;
        this.bluePointLabel = bluePointLabel;
        setImage();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    // 画像を読み込むメソッド
    public void setImage() {
        try
        {
            image1 = ImageIO.read(new File("hockey_bar1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try
        {
            image2 = ImageIO.read(new File("hockey_bar2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        if(hockeyBall.y < 60) {
            if(hockeyBar1.x < hockeyBall.x && hockeyBar1.x + 300 > hockeyBall.x){
              hockeyBall.speedY = -hockeyBall.speedY;
              hockeyBall.speedX += hockeyBar1.speedX;
            } else {
                restart("red");
            }
        }

        if(hockeyBall.y > 440) {
            if(hockeyBar2.x < hockeyBall.x && hockeyBar2.x + 300 > hockeyBall.x){
              hockeyBall.speedY = -hockeyBall.speedY;
              hockeyBall.speedX += hockeyBar2.speedX;
            } else {
                restart("blue");
            }
        }

        g.setColor(Color.BLUE);
        g.drawImage(image1, hockeyBar1.x, hockeyBar1.y, this);
        g.drawImage(image2, hockeyBar2.x, hockeyBar2.y, this);
        g.setColor(Color.BLACK);
        g.drawOval(hockeyBall.x, hockeyBall.y, 15, 15);
        
        time += 10;
        if(hockeyBall.x > 1000 || hockeyBall.x < 0){
          hockeyBall.speedX = -hockeyBall.speedX;
          hockeyBall.x += hockeyBall.speedX / 10;
          hockeyBall.y += hockeyBall.speedY / 10;
        } else {
          hockeyBall.x += hockeyBall.speedX / 10;
          hockeyBall.y += hockeyBall.speedY / 10;
        }

        if (isLEFT1) {
            if(hockeyBar1.x >= 0){
                this.hockeyBar1.x -= 10;
                this.hockeyBar1.speedX = -20;
            }
            System.out.println("H(左)");
        } else if (isRIGHT1) {
            if(hockeyBar1.x <= 700){
                this.hockeyBar1.x += 10;
                this.hockeyBar1.speedX = 20;
            }
            System.out.println("L(右)");
        }
        
        if (isLEFT2) {
            if(hockeyBar2.x >= 0){
                this.hockeyBar2.x -= 10;
                this.hockeyBar2.speedX = -20;
            }
        } else if (isRIGHT2) {
            if(hockeyBar2.x <= 700){
                this.hockeyBar2.x += 10;
                this.hockeyBar2.speedX = 20;
            }
        }

    }

    public void restart(String winner) {
        hockeyBall = new HockeyBall();
        hockeyBar1 = new HockeyBar(50);
        hockeyBar2 = new HockeyBar(450);
        
        if(winner == "blue" ){
            bluePoint += 10;
            bluePointLabel.setText("青: " + bluePoint + "点");
        } else {
            redPoint += 10;
            redPointLabel.setText("赤: " + redPoint + "点");
        }

        if(bluePoint >= 50) {
            JLabel congrat = new JLabel("青の勝利です!!\nおめでとう!!");
            congrat.setForeground(Color.RED);
            this.status = 0;
            this.bluePoint = 0;
            this.redPoint = 0;
            this.isRIGHT1 = false;
            this.isRIGHT2 = false;
            this.isLEFT1 = false;
            this.isLEFT2 = false;
            JOptionPane.showMessageDialog(this, congrat);
            this.redPointLabel.setText("赤: 0点");
            this.bluePointLabel.setText("青: 0点");
        }

        if(redPoint >= 50) {
            JLabel congrat = new JLabel("赤の勝利です!!\nおめでとう!!");
            congrat.setForeground(Color.RED);
            this.status = 0;
            this.bluePoint = 0;
            this.redPoint = 0;
            this.isRIGHT1 = false;
            this.isRIGHT2 = false;
            this.isLEFT1 = false;
            this.isLEFT2 = false;
            JOptionPane.showMessageDialog(this, congrat);
            this.redPointLabel.setText("赤: 0点");
            this.bluePointLabel.setText("青: 0点");
        }

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void keyPressed(KeyEvent evt) {
        if (76 == evt.getKeyCode()) {
            isRIGHT1 = true;
            System.out.println("L(右)");
        } else if (72 == evt.getKeyCode()) {
            isLEFT1 = true;
            System.out.println("H(左)");
        }
        
        if (65 == evt.getKeyCode()) {
            isLEFT2 = true;
            System.out.println("A(左)");
        } else if (70 == evt.getKeyCode()) {
            isRIGHT2 = true;
            System.out.println("F(右)");
        }
    }

    public void keyReleased(KeyEvent evt) {
         if (76 == evt.getKeyCode()) {
            isRIGHT1 = false;
            System.out.println("L離す(右)");
        } else if (72 == evt.getKeyCode()) {
            isLEFT1 = false;
            System.out.println("H離す(左)");
        }
        
        if (65 == evt.getKeyCode()) {
            isLEFT2 = false;
            System.out.println("A離す(左)");
        } else if (70 == evt.getKeyCode()) {
            isRIGHT2 = false;
            System.out.println("F離す(右)");
        }   
    }

    public void keyTyped(KeyEvent evt) { }
}
