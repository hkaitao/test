package com.stream.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alpha on 2017/5/26.
 */
/*使用新版本的httpclient发送数据，使用新版本的线程池*/
public class HttpClientNewSender {
    private static Log logger = LogFactory.getLog(HttpClientNewSender.class);

    private static PoolingHttpClientConnectionManager httpClientConnectionManager;
    private static RequestConfig requestConfig;
    private static LaxRedirectStrategy redirectStrategy;
    private static int HTTP_MAX_CONNNECTIONS = 3000;
    private static int HTTP_MAX_CONNECTIONS_PER_ROUTE = 1000;
    private static int HTTPCLIENT_CONNECT_TIMEOUT = 10*1000;
    private static int HTTPCLIENT_SOCKET_TIMEOUT = 10*1000;


    static {
        httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(HTTP_MAX_CONNNECTIONS);
        httpClientConnectionManager.setDefaultMaxPerRoute(HTTP_MAX_CONNECTIONS_PER_ROUTE);

        requestConfig = RequestConfig.custom()
                .setConnectTimeout(HTTPCLIENT_CONNECT_TIMEOUT)
                .setSocketTimeout(HTTPCLIENT_SOCKET_TIMEOUT)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();

        redirectStrategy = new LaxRedirectStrategy();
    }

    private static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if(executionCount > 3){
                logger.error("重试次数过多");
                return false;
            }

            HttpClientContext httpClientContext = HttpClientContext.adapt(context);
            HttpRequest httpRequest = httpClientContext.getRequest();

            /*验证请求是否是重复发送*/
            boolean flag = !(httpRequest instanceof HttpEntityEnclosingRequest);
            if(flag){
                return true;
            }
            return false;
        }
    };


    public static CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(redirectStrategy)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        return httpClient;
    }

    public static String send(Object object, String url) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Connection", "keep-alive");

        String jsonStr = null;
        try {
            InputStream is=new ByteArrayInputStream(JSON.toJSONString(object).getBytes());
            InputStreamEntity streamEntity= new InputStreamEntity(is);
            streamEntity.setContentType("application/json");
            httpPost.setEntity(streamEntity);

            httpResponse = httpClient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();

                /*String charset = null;
                ContentType contentType = ContentType.getOrDefault(httpEntity);
                if (contentType != null) {
                    Charset contentTypeCharset = contentType.getCharset();
                    if (contentTypeCharset != null) {
                        charset = contentTypeCharset.toString();
                    }
                }*/
                jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info(jsonStr);
            }
        } catch (IOException e) {
            logger.error("处理请求异常", e);
        }
        return jsonStr;
    }
}
