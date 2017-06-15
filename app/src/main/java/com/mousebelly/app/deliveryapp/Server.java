package com.mousebelly.app.deliveryapp;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vasudev on 28/03/2017.
 */

public class Server {

    static final public Server s = getServer();
    int status;

    private Server() {

    }

    private static Server getServer() {
        return new Server();
    }

    public String get(String reqUrl) {
        System.out.println(reqUrl);
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            System.setProperty("http.keepAlive", "false");

            conn.setRequestProperty("connection", "close");
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn != null) {
                System.out.println("getting data form server...........");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());

                // System.out.println( conn.getInputStream() );

                response = convertStreamToString(in);
                //Log.i("res",response);
            } else {
                System.out.println("Unable to Get Data from Server");
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("Exception: " , e.getMessage());
        }

        return response;

    }

    //used to convert input stream to Sting
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public int post(String url, JSONObject json) {


        DefaultHttpClient httpclient = new DefaultHttpClient();

        System.out.println("in post");

        System.out.println(json);

        //url with the post data
        HttpPost httpost = new HttpPost(url);
        try {
            //convert parameters into JSON object
            // JSONObject holder = getJsonObjectFromMap(params);

            //passes the results to a string builder/entity
            StringEntity se = new StringEntity(json.toString());
            Log.d("Output", json.toString());


            //sets the post request as the resulting string
            httpost.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");

            //Handles what is returned from the page
            ResponseHandler responseHandler = new BasicResponseHandler();
            HttpResponse resp = httpclient.execute(httpost);


            System.out.println(resp.getStatusLine().getStatusCode());
            status = resp.getStatusLine().getStatusCode();
            String statusss = resp.getStatusLine().getReasonPhrase();
            System.out.println("This is Dusra wala Status:::::::" + statusss);

            Log.d("Output", httpost.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }


    public String put(String reqUrl) {
        System.out.println(reqUrl);
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("connection", "close");
            conn.setRequestMethod("PUT");
            conn.connect();

            System.out.println("updating data form server");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertStreamToString(in);
            Log.i("res", response);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception: ", e.getMessage());
        }

        return response;

    }

    public int putwithdata(String url, JSONObject json) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        System.out.println("inside put");
        System.out.println("Server vala url : " + url);
        System.out.println("Server JSON : " + json);
        //url with the post data
        HttpPut httpPut = new HttpPut(url);
        try {
            StringEntity se = new StringEntity(json.toString());
            Log.d("Output", json.toString());

            httpPut.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            //Handles what is returned from the page
            ResponseHandler responseHandler = new BasicResponseHandler();
            HttpResponse resp = httpclient.execute(httpPut);

            System.out.println(resp.getStatusLine().getStatusCode());
            status = resp.getStatusLine().getStatusCode();

            Log.d("Output", httpPut.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
