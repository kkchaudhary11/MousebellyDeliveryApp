package com.mousebelly.app.deliveryapp;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * Created by Kamal Kant on 05-04-2017.
 */

public abstract class SocketAccess {

    public static Socket socket;
    public static boolean connected = false;

    public SocketAccess() {
        connect();
    }

    public static void disconnect() {
        socket.disconnect();
    }

    public Socket getSocket() {
        return socket;
    }

    private void connect() {

        System.out.println("Socket Connect 1: " + SocketAccess.connected);

        if (SocketAccess.connected == false) {
            try {
                socket = IO.socket(APIs.DOMAIN + "/deliveryBoySocket");
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        SocketAccess.connected = true;
                        System.out.println("Socket Connected");
                    }
                });

                /*socket.on("broadcasting", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        receive(args[0]);
                        //System.out.println(args[0]);
                    }
                });*/
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Socket Already Connected");
        }

        System.out.println("Socket Connect 2: " + SocketAccess.connected);
    }

    public abstract void receive(Object o);

}
