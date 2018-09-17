package com.tomcat.study.chapter2;

/**
 * 处理静态请求
 */
public class StaticResourceProcessor {



    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
