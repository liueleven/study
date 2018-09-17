package com.tomcat.study.chapter2;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 既可以对静态资源的请求，也可以对Servlet的请求进行处理
 */
public class HttpServer1 {



    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
        server.await();
    }

    /**
     * 会一直等待HTTP请求，将资源分发给相应的类处理
     */
    public void await() {
        ServerSocket serverSocket = null;
        int port = 8088;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
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
                //检测是不是静态资源
                if(request.getUri().startsWith("/servlet")) {
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request,response);
                }else {
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }
                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            }catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

}
