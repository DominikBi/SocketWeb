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
    private int numberOfUsersBanned;
    private int numberOfUsersRegistered;
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
                    String answer = sc.next();
                    if(answer.equals("Y")){
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
            String sep = System.getProperty("file.separator");
            String mainPath = System.getProperty("user.home") + sep +"ServerData" + sep;

            try(DataInputStream inputBanned = new DataInputStream(new FileInputStream(mainPath + "bannedUsers"))) {
                numberOfUsersBanned = inputBanned.readInt();
                for (int i = 0; i < numberOfUsersBanned; i++) {
                    User user = new User(inputBanned.readUTF(), new Socket("localhost", inputBanned.readInt()));
                    inputBanned.readUTF();
                    bannedUsers.add(user);
                }
            }
            try(DataInputStream inputRegistered = new DataInputStream(new FileInputStream(mainPath + "registeredUsers"))){
                numberOfUsersRegistered = inputRegistered.readInt();
                for(int i = 0; i < numberOfUsersRegistered;i++){
                    User user = new User(inputRegistered.readUTF(),new Socket("localhost", inputRegistered.readInt()));
                    inputRegistered.readUTF();
                    existingUsers.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void save(){
        String sep = System.getProperty("file.separator");
        String mainPath = System.getProperty("user.home") + sep +"ServerData" + sep;
        String enter = System.getProperty("line.separator");
        int numberBanned = bannedUsers.size();
        int numberRegistered = existingUsers.size();
        File banned = new File(mainPath + "bannedUsers");
        File registered = new File(mainPath + "registeredUsers");
        try(DataOutputStream inputBanned = new DataOutputStream(new FileOutputStream(banned))){
            inputBanned.writeInt(numberBanned);
            for(User bannedUser : bannedUsers){
                inputBanned.writeUTF(bannedUser.name);
                inputBanned.writeInt(bannedUser.address.getPort());
                inputBanned.writeUTF(enter);
            }
        try (DataOutputStream outputRegistered = new DataOutputStream(new FileOutputStream(registered))){
            outputRegistered.writeInt(numberRegistered);
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
        server.init();


    }
}
