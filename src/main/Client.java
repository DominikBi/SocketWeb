package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class Client {
    public static void main(String[] args){
            Client clientC = new Client();
            clientC.start();



    }
    private void start(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton callToEat = new JButton("Dinner is ready");
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getWidth()/2,Toolkit.getDefaultToolkit().getScreenSize().height/2 - frame.getHeight()/2);
        callToEat.setPreferredSize(new Dimension(frame.getWidth()/3,frame.getHeight()/3));
        callToEat.setLocation(frame.getX()-frame.getHeight()/2,frame.getY()-frame.getHeight()/2);

        panel.add(callToEat);
        frame.setVisible(true);
        callToEat.addActionListener(e -> {
            try {
                Socket client = new Socket("localhost", 1337);
                DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
                outputStream.writeUTF("Eat");
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
