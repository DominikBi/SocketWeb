package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client{
    public static void main(String[] args) {
        Client clientC = new Client();
        clientC.start();


    }

    private void start() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField waitingTextField = new JTextField();
        JTextField textField = new JTextField();
        JButton button = new JButton("Apply");
        textField.setPreferredSize(new Dimension(200, 100));
        waitingTextField.setText("Waiting on allowance to connect");
        panel.add(textField);
        panel.add(button);
        frame.add(panel);

        button.addActionListener(e -> {
            try {
                Socket socket = new Socket("localhost", 1337);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                System.out.println(textField.getText());
                output.writeUTF(textField.getText());
                output.writeInt(socket.getPort());

                panel.add(waitingTextField);
                frame.add(panel);


                if(input.readInt() == 101){
                    socket.close();
                    frame.setVisible(false);
                }
                panel.remove(waitingTextField);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}

