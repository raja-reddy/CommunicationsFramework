package com.iKeirNez.networking.test;

import com.iKeirNez.networking.HookType;
import com.iKeirNez.networking.connections.ClientConnection;
import com.iKeirNez.networking.connections.Connection;
import com.iKeirNez.networking.connections.ConnectionManager;
import com.iKeirNez.networking.connections.ServerConnection;
import com.iKeirNez.networking.packets.PacketHandler;
import com.iKeirNez.networking.packets.PacketListener;

import java.io.IOException;

/**
 * Created by iKeirNez on 06/04/2014.
 */
public class Test implements PacketListener {

    public static ServerConnection serverConnection;
    public static ClientConnection clientConnection;

    public static void main(String[] args){
        try {
            ConnectionManager connectionManager = new ConnectionManager();
            connectionManager.registerListener(new Test());
            serverConnection = connectionManager.serverConnection("localhost", 25565);

            connectionManager.addHook(HookType.CONNECTED, (c) -> {
                if (c instanceof ClientConnection){
                    System.out.println("Client connected");
                    serverConnection.sendPacket(new TestPacket(555));
                }
            });

            clientConnection = connectionManager.clientConnection("localhost", 25565);

            connectionManager.addHook(HookType.LOST_CONNECTION, (s) -> System.out.println("Lost connection hook"));

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                clientConnection.close();
                serverConnection.close();
                System.out.println("Closed");
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PacketHandler
    public void onPacket(TestPacket packet){
        System.out.println("TestPacket receieved, data: " + packet.getNumber());
    }

}