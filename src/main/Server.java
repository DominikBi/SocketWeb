package main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ServerSocket server;
    private ArrayList<User> onlineUsers = new ArrayList<>();
    private ArrayList<User> existingUsers = new ArrayList<>();
    private ArrayList<User> bannedUsers = new ArrayList<>();
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
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                User user = new User(input.readUTF(),new Socket("localhost",input.readInt()));
                if(existingUsers.contains(user)) {
                    onlineUsers.add(user);
                } else {
                    System.out.println("Should " + user.name +" be able to connect?(Y/N)");
                    Scanner sc = new Scanner(System.in);
                    String awnser = sc.next();
                    if(awnser.equals("Y")){
                        onlineUsers.add(user);
                        output.writeInt(102);
                    }else{
                        output.writeInt(101);
                        bannedUsers.add(user);

                    }


                }


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
                existingUsers.add(new User(dataInputStream.readUTF(),new Socket("localhost",dataInputStream.readInt())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void save(){
        String sep = System.getProperty("file.separator");
        String mainPath = System.getProperty("user.home") + sep +"ServerData" + sep;
        String enter = System.getProperty("line.separator");
        File banned = new File(mainPath + "bannedUsers");
        File registered = new File(mainPath + "registeredUsers");
        try(DataOutputStream inputBanned = new DataOutputStream(new FileOutputStream(banned))){
            for(User bannedUser : bannedUsers){
                inputBanned.writeUTF(bannedUser.name);
                inputBanned.writeInt(bannedUser.address.getPort());
                inputBanned.writeUTF(enter);
            }
        try (DataOutputStream outputRegistered = new DataOutputStream(new FileOutputStream(registered))){
            for(User registeredUser : existingUsers){
                outputRegistered.writeUTF(registeredUser.name);
                outputRegistered.writeInt(registeredUser.address.getPort());
                outputRegistered.writeUTF(enter);
            }

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
