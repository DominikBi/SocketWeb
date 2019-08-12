package main;

import javax.swing.*;
import java.awt.*;
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
        String s = "Song of Napalm, by Bruce Weigl targetes the cruelty of the use of Napalm in the Vietnam war. In the poem, the poet explores the view of a military commander on the battlefield and the view of innocent residents getting struck by Napalm. In the poem the poet describes that he doesn't want to fight in the war but has to for his own survival. Weigl expresses the situation on the battlefield through metaphors and auditory imagery.";
        System.out.println(s.split(" ").length);
        int i = 0;
        while(i < 10){
            i = i +1;

        }
        for(int e = 0; e<10; e++){

        }
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

