package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
            Client clientC = new Client();
            clientC.start();



    }
    private void start(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField textField = new JTextField();
        JButton button = new JButton("Apply");
        textField.setPreferredSize(new Dimension(200,100));
        panel.add(textField);
        panel.add(button);
        frame.add(panel);
        button.addActionListener(e -> {
            try {
                Socket socket = new Socket("localhost", 1337);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                System.out.println(textField.getText());
                output.writeUTF(textField.getText());
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
