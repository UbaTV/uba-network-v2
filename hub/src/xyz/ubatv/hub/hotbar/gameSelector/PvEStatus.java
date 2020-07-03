package xyz.ubatv.hub.hotbar.gameSelector;

import xyz.ubatv.hub.utils.SocketsUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PvEStatus {

    public void handshakePvE(){
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 25565), 2000);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            ByteArrayOutputStream handshakeBytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshakeBytes);

            handshake.writeByte(0x00);
            SocketsUtils.writeVarInt(handshake, 736); // 1.16.1 Protocol number
            SocketsUtils.writeVarInt(handshake, "localhost".length());
            handshake.writeBytes("localhost");
            handshake.writeShort(25567);

            SocketsUtils.writeVarInt(handshake, 1);
            SocketsUtils.writeVarInt(out, handshakeBytes.size());
        }catch (IOException e){
            // Server Offline
            e.printStackTrace();
        }

    }
}
