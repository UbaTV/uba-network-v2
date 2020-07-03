package xyz.ubatv.hub.hotbar.gameSelector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingServer {

    private String host;
    private int port;
    private Socket socket = new Socket();
    private String[] data = new String[999];

    public PingServer(String host, int port){
        this.host = host;
        this.port = port;

        try{
            socket.connect(new InetSocketAddress(host,port));
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            out.write(0xFE);

            int b;
            StringBuilder str = new StringBuilder();
            while((b = in.read()) != -1){
                if(b > 16 && b != 255 && b != 23 && b != 24){
                    str.append((char)b);
                }
            }

            data = str.toString().split("§");
            data[0] = data[0].substring(1,data[0].length());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getMotd(){
        if(data[0] == null) return "§cOFFLINE";
        return data[0];
    }

    public int getOnline(){
        if(data[1] == null) return 0;
        return Integer.parseInt(data[1]);
    }

    public int getMax(){
        if(data[2] == null) return 0;
        return Integer.parseInt(data[2]);
    }

    public void update(){
        try{
            socket.close();
            socket = new Socket();
            try{
                socket.connect(new InetSocketAddress(host,port));
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                out.write(0xFE);

                int b;
                StringBuilder str = new StringBuilder();
                while((b = in.read()) != -1){
                    if(b > 16 && b != 255 && b != 23 && b != 24){
                        str.append((char) b);
                    }
                }

                data = str.toString().split("§");
            }catch (ConnectException e){
                System.out.println("Server Offline");
            }
        }catch(IOException e){
            System.out.println("Server Offline");
        }
    }
}
