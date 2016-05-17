package com.system.dormitory.dormitory_system_android.http;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

/**
 * Created by 보운 on 2016-05-17.
 */
public class HttpServerConnection {
    private static HttpClient ourInstance = HttpClientBuilder.create().build();

    public static HttpClient getInstance() {
        return ourInstance;
    }

    private HttpServerConnection() {
    }
}
