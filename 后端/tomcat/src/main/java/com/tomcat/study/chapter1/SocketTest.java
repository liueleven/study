package com.tomcat.study.chapter1;

import java.io.*;
import java.net.Socket;

public class SocketTest {

    private static final String HOST = "127.0.0.1";
    private static final int PROT = 8080;
    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket(HOST,PROT);
        OutputStream os = socket.getOutputStream();
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        out.println("GET /api-doc/ HTTP/1.1");
        out.println("HOST: localhost:8080");
        out.println("Connection: Close");
        out.println();
        StringBuffer sb = new StringBuffer(8096);
        boolean loop = true;
        while (loop) {
            if(in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }
        System.out.println(sb.toString());
        socket.close();
    }
}
