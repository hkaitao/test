package com.stream.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created by alpha on 2017/5/25.
 */
public class HttpClientSender {

    public static int httpSend(Object object){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod();
        int statusCode = HttpStatus.SC_OK;
        /*设置传入的参数*/

        try {
            statusCode = httpClient.executeMethod(postMethod);

            if(statusCode != HttpStatus.SC_OK){
                System.out.println("返回异常：" + postMethod.getStatusLine());
                return statusCode;
            }

            byte[] responsebody = postMethod.getResponseBody();

        } catch (IOException e) {
            System.out.println("获取数据异常");
        }

        return statusCode;
    }
}
