package com.stream.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by alpha on 2017/5/25.
 */
public class HttpClientSender {

    private static Log logger = LogFactory.getLog(HttpClientSender.class);

    public static int send(Object object, String url, int timeout){
        String jsonStr = JSON.toJSONString(object);
        logger.info("本次发送的数据为:" + jsonStr);

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        int statusCode = HttpStatus.SC_OK;
        /*设置传入的参数*/

        try {
            RequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);

            statusCode = httpClient.executeMethod(postMethod);

            if(statusCode != HttpStatus.SC_OK){
                logger.error("返回异常：" + postMethod.getStatusLine());
                return statusCode;
            }

            String responsebody = postMethod.getResponseBodyAsString();

        } catch (IOException e) {
            logger.error("获取数据异常",e);
        }catch (Exception e){
            logger.error("程序异常",e);
        }finally {
            postMethod.releaseConnection();
        }

        return statusCode;
    }
}
