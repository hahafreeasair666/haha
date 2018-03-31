/*
 * Copyright (c) 2006-2017, Yunnan Sanjiu Network technology Co., Ltd.
 * 
 * All rights reserved.
 */
package com.ch999.haha.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientUtil
 *
 * @author zhenjin
 */
@Slf4j
public class HttpClientUtil {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static final String CONTENT_TYPE_JSON = "application/json";

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static void doHttpClientClose(CloseableHttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URL url1 = null;
            try {
                url1 = new URL(url);
                URI uri = new URI(url1.getProtocol(), null,url1.getHost(), url1.getPort(),url1.getPath(), url1.getQuery(), null);
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
                res = execute(httpClient, httpGet);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } finally {
            doHttpClientClose(httpClient);
        }
        return res;
    }

    public static String get(String url, String contentType) {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URL url1 = null;
            try {
                url1 = new URL(url);
                URI uri = new URI(url1.getProtocol(), url1.getHost(),url1.getPath(), url1.getQuery(), null);
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeader("Content-Type", contentType);
                res = execute(httpClient, httpGet);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } finally {
            doHttpClientClose(httpClient);
        }
        return res;
    }


    /**
     * 可以设置多个请求头并发送get请求
     * @param url
     * @param headMap
     * @return
     */
    public static String get(String url, Map<String,String> headMap) {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URL url1 = null;
            try {
                url1 = new URL(url);
                URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
                HttpGet httpGet = new HttpGet(uri);
                headMap.forEach(httpGet::setHeader);
                res = execute(httpClient, httpGet);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } finally {
            doHttpClientClose(httpClient);
        }
        return res;
    }

    /**
     * 发送post请求
     *
     * @param url
     *            post url
     * @param params
     *            post参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = httpPostHandler(url, params);
            res = execute(httpClient, httpPost);
        } finally {
            doHttpClientClose(httpClient);
        }
        return res;
    }

    /**
     * 发送delete请求
     *
     * @param url
     *            请求地址
     * @return String
     */
    public static String sndDelete(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete del = new HttpDelete(url);
        try {
            // 提交请求并以指定编码获取返回数据
            HttpResponse httpResponse = httpClient.execute(del);
            logger.info("请求地址：" + url + "；响应状态：" + httpResponse.getStatusLine());
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, CHARSET_UTF_8);
        } catch (ClientProtocolException e) {
            logger.error("协议异常,堆栈信息如下", e);
        } catch (IOException e) {
            logger.error("网络异常,堆栈信息如下", e);
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
                httpClient = null;
            }
        }
        return null;
    }

    /**
     * post json数据
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String postJson(String url, String jsonStr) {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity;
            try {
                stringEntity = new StringEntity(jsonStr);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
            httpPost.setHeader("Content-Type", CONTENT_TYPE_JSON);
            httpPost.setEntity(stringEntity);
            res = execute(httpClient, httpPost);
        } finally {
            doHttpClientClose(httpClient);
        }
        return res;
    }

    /**
     * 文件上传
     *
     * @param rootURL
     *            根路径
     * @param reqURL
     *            请求地址
     * @return
     */
    public static String uploadFile(String rootURL, String reqURL, MultipartFile multipartFile,File file) {

        String info = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection httpUrlConn = null;
        BufferedReader bufferedReader = null;
        InputStream inputStreamRequest = null;
        InputStreamReader inputStreamReader = null;

        try {

            // 连接
            URL url = new URL(rootURL + reqURL);

            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("POST");
            // 设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");

            String boundary = "-----------------------------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = httpUrlConn.getOutputStream();
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(String.format(
                    "Content-Disposition: form-data; name=\"attachment\"; filename=\"%s\"\r\n",
                    multipartFile.getOriginalFilename()).getBytes("UTF-8"));

            outputStream.write(String.format("Content-Type: %s \r\n\r\n", multipartFile.getContentType()).getBytes(
                    "UTF-8"));

            byte[] data = new byte[1024];
            int len = 0;
            if(file == null) {
                inputStream = multipartFile.getInputStream();
            }else {
                inputStream = new FileInputStream(file);
            }
            while ((len = inputStream.read(data)) > -1) {
                outputStream.write(data, 0, len);
            }

            outputStream.write(("\r\n--" + boundary + "\r\n").getBytes());
            outputStream.write("Content-Disposition: form-data; name=\"parameters\";\r\n\r\n".getBytes("UTF-8"));
            outputStream.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
            // 将返回的输入流转换成字符串
            inputStreamRequest = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStreamRequest, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            info = buffer.toString();

        } catch (UnsupportedEncodingException e) {
            logger.error("上传文件失败!", e);
        } catch (MalformedURLException e) {
            logger.error("上传文件失败!", e);
        } catch (ProtocolException e) {
            logger.error("上传文件失败!", e);
        } catch (IOException e) {
            logger.error("上传文件失败!", e);
        } finally {
            // 释放资源
            httpUrlConn.disconnect();
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(inputStreamRequest);
        }
        return info;
    }

    /**
     * 文件读取（读取为二进制数组）
     *
     * @param url
     *            文件地址
     * @return 文件的二进制数组
     */
    public static byte[] download(String url) {
        byte[] data = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ByteArrayOutputStream bos = null;
        try {
            URL url1 = null;
            try {
                url1 = new URL(url);
                URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                bos = new ByteArrayOutputStream();
                entity.writeTo(bos);
                data = bos.toByteArray();
            } catch (MalformedURLException e) {
                logger.debug("文件下载失败！", e);
            } catch (URISyntaxException e) {
                logger.debug("文件下载失败！", e);
            } catch (ClientProtocolException e) {
                logger.debug("文件下载失败！", e);
            } catch (IOException e) {
                logger.debug("文件下载失败！", e);
            }
        } finally {
            doHttpClientClose(httpClient);
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.debug("流关闭失败", e);
                }
            }
        }
        return data;
    }

    private static HttpPost httpPostHandler(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, CHARSET_UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httpPost;
    }

    private static String execute(CloseableHttpClient httpClient, HttpUriRequest httpGetOrPost) {
        String res = null;
        int statusCode = 0;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGetOrPost);
            HttpEntity entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            res = EntityUtils.toString(entity, CHARSET_UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            doResponseClose(response);
        }

        if (statusCode == HttpStatus.OK.value()) {
            return res;
        } else {
            log.error("http请求失败!状态码为:{},返回结果为:{}", statusCode, res);
            return null;
        }
    }

    private static void doResponseClose(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    // byte[] res =
    // download("https://img2.ch999img.com//newstatic/649/2698a13baa2f94.amr?time=4");
    // System.out.println(Arrays.toString(res));
    // }
    /***
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = null;
        String localIP = "127.0.0.1";
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
                if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP))
                        || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP))
                            || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("WL-Proxy-Client-IP");
                        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP))
                                || "unknown".equalsIgnoreCase(ip)) {
                            ip = request.getRemoteAddr();
                        }
                    }
                }
            }
        }
        // 多个IP地址时获取第一个IP
        if (ip != null && (!"".equals(ip)) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
