package com.tomcat.study.chapter2;

import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;

import java.io.*;
import java.net.URL;

public class Response implements HttpResponse{

    private static final int BUFFER_SIZE = 1024;

    Request request;
    OutputStream output;
    PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void sendStaticResource() throws IOException{
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(Constants.WEB_ROOT, request.getUri());
            fis = new FileInputStream(file);
            int ch = fis.read(bytes,0,BUFFER_SIZE);
            while (ch != -1) {
                output.write(bytes,0,ch);
                ch  = fis.read(bytes,0,BUFFER_SIZE);
            }
        }catch (FileNotFoundException e) {
            String errorMsg = "HTTP / 1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
                    + "<h1>File Not Found</h1>";
            output.write(errorMsg.getBytes());
        }finally {
            if( fis != null) {
                fis.close();
            }
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public PrintWriter getWriter() {
        writer = new PrintWriter(output,true);
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public URL getRequest() {
        return null;
    }

    public int getStatusCode() {
        return 0;
    }

    public int getContentLength() {
        return 0;
    }

    public long getExpiration() {
        return 0;
    }

    public long getLastModified() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public String getResponseHeader(String s) {
        return null;
    }

    public BufferedInputStream getInputStream() {
        return null;
    }

    public void disconnect() {

    }

    public String getContentEncoding() {
        return null;
    }

    public MessageHeader getHeaders() {
        return null;
    }

}
