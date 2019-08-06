package main;

import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    Server(int port){
        try {
            server = new ServerSocket(port);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void start(){
        while (true){

            try {
                System.out.println("Waiting for client at " + server.getLocalPort());
                Socket client = server.accept();
                DataInputStream inputStream = new DataInputStream(client.getInputStream());
                if(inputStream.readUTF().equals("Eat")){
                    System.out.println("Ready to eat");

                    String sep = System.getProperty("file.separator");
                    InputStream in = new FileInputStream("C:" + sep+ "Users"+ sep+"birke"+sep+"IdeaProjects" + sep +"SocketWeb" +sep +"Data" +sep +"service-bell_daniel_simion.wav");
                    AudioStream as = new AudioStream(in);
                    AudioPlayer.player.start(as);

                }

                client.close();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

    }
    public static void main(String[] args){
        Server s = new Server(1337);
        s.start();
    }
}
