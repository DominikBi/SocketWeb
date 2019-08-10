package main;

import java.net.Socket;

public class User {
    String name;
    Socket address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getAddress() {
        return address;
    }

    public void setAddress(Socket address) {
        this.address = address;
    }


    User(){

    }
    User(String name, Socket address){
        this.name = name;
        this.address = address;

    }
}
