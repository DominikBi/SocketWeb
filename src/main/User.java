package main;

import javax.jws.soap.SOAPBinding;
import java.net.SocketAddress;

public class User {
    String name;
    SocketAddress address;
    User(){

    }
    User(String name, SocketAddress address){
        this.name = name;
        this.address = address;

    }
}
