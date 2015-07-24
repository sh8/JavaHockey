import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainProg {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Practics 2");
        JButton exec = new JButton("開始");
        JButton stop = new JButton("停止");
        JLabel redPointLabel = new JLabel("赤: 0点");
        JLabel bluePointLabel = new JLabel("青: 0点");
        JLabel usage = new JLabel("青は左(H),右(L)で操作. 赤は左(A),右(F)で操作");
        final MainPanel mp = new MainPanel(redPointLabel, bluePointLabel);
        exec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mp.requestFocusInWindow();
                mp.status = 1;
            }
        });

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mp.requestFocusInWindow();
                mp.status = 0;
            }
        });

        frame.setTitle("ホッケーゲーム");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(exec);
        frame.getContentPane().add(stop);
        frame.getContentPane().add(redPointLabel);
        frame.getContentPane().add(bluePointLabel);
        frame.getContentPane().add(usage);
        frame.getContentPane().add(mp);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setBounds(0, 0, 1012, 600);
        frame.setVisible(true);  
        final Timer timer = new Timer(20,
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(mp.status == 1) {
                            mp.repaint(); 
                        }
                    }
                });
        timer.start();
    }
}
