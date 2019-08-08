package main;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Server {
    private ServerSocket server;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> existingUsers = new ArrayList<>();
    private int numberOfUsers;
    Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void start(){

        while (true){
            System.out.println("Waiting on client at " + server.getLocalSocketAddress());

            try {
                Socket client = server.accept();
                System.out.println(client.getRemoteSocketAddress());
                DataInputStream input = new DataInputStream(client.getInputStream());
                User user = new User(input.readUTF(),client.getLocalSocketAddress());
                users.add(user);
                System.out.println(user.name);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void init(){
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "NetworkServerData" +System.getProperty("file.separator") + "Data.txt");
            DataInputStream dataInputStream = new DataInputStream(fis);
            numberOfUsers= dataInputStream.readInt();
            for(int i = 0; i < numberOfUsers; i++){
                //existingUsers.add(new User(dataInputStream.readUTF(),dataInputStream.readUTF()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server server = new Server(1337);
        server.start();


    }
}
