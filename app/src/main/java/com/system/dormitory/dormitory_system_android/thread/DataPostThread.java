package com.system.dormitory.dormitory_system_android.thread;

import android.os.AsyncTask;
import android.util.Log;

import com.system.dormitory.dormitory_system_android.http.HttpServerConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by 보운 on 2016-05-17.
 */
public class DataPostThread extends AsyncTask<ArrayList<BasicNameValuePair>, Integer, Void> {
    @Override
    protected Void doInBackground(ArrayList<BasicNameValuePair>... params) {
//        HttpClient httpClient = ConnectSSLClient.getHttpClient();
        HttpClient httpClient = HttpServerConnection.getInstance();
        String responseString = null;
        String urlString = "";

        try {
            HttpPost httpPost = new HttpPost(urlString);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            for (int i = 0; i < params[0].size(); i++) {
                Log.i("DataPostThread getName", params[0].get(i).getName());
                Log.i("DataPostThread getValue", params[0].get(i).getValue());
                nameValuePairs.add(new BasicNameValuePair(params[0].get(i).getName(), params[0].get(i).getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));

            HttpResponse response = httpClient.execute(httpPost);
            responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            Log.i("DataPostThread", String.valueOf(params[0]));
            Log.i("DataPostThread String", responseString);
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null;
    }

    public DataPostThread() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Void s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
