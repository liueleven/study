package com.tomcat.study.chapter1;



import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FirstSimpleWebServer {
}

class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    private void await() {
        System.out.println("await method ...");
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream out = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                out = socket.getOutputStream();
                Request request = new Request(input);
                request.parse();

                Response response = new Response(out);
                response.setRequest(request);
                response.sendStaticResource();
                socket.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

class Response {
    private static final int BUFFER_SIZE = 1024;

    Request request;

    OutputStream out;

    public Response(Request request) {
        this.request = request;
    }

    public Response(OutputStream out) {
        this.out = out;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT,request.getUri());
            if(file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                while (ch != -1) {
                    out.write(bytes,0,ch);
                    ch = fis.read(bytes,0,BUFFER_SIZE);
                }
            }else {
                String errorMsg = "HTTP / 1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
                        + "<h1>File Not Found</h1>";
                out.write(errorMsg.getBytes());
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }finally {
            if(fis != null) {
                fis.close();
            }
        }
    }

    public static int getBufferSize() {
        return BUFFER_SIZE;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }
}

class Request {
    private InputStream input;

    private String uri;

    public void parse() {
        System.out.println("Request.parse() ...");
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);

        }catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for(int j=0; j<i ; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());
    }

    public String parseUri(String requestString) {
        System.out.println("Request.parseUri() ...");
        int index1,index2;
        index1 = requestString.indexOf(' ');
        if(index1 != -1) {
            index2 = requestString.indexOf(' ',index1 + 1);
            if(index2 > index1) {
                return requestString.substring(index1 + 1,index2);
            }
        }
        return null;
    }

    public Request(InputStream input) {
        this.input = input;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
