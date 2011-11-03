/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.util;

import com.yahoo.messenger.exception.HttpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//import javax.microedition.io.Connector;
//import javax.microedition.io.HttpConnection;


public class HttpUtils {

    public static String performHttpGet(String cs)
        throws IOException, HttpException
    {

        return performHttpGet(cs, null);

    }

    public static String performHttpGet(String cs, YahooMessengerAuthentication authentication)
        throws IOException, HttpException
    {

        if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
            System.out.println("HTTP GET SENT:");
            System.out.println(cs);
        }

        String s = new String();
        URL url = new URL(cs);
        HttpURLConnection hc = (HttpURLConnection)url.openConnection();

        hc.setRequestProperty("User-Agent", "Stack/1.0.0.0");
        if (authentication != null) {

            if (!authentication.isUsingOAuth()) {
                hc.setRequestProperty("Cookie", authentication.getCookie());
            }
            else {
                hc.setRequestProperty("Authorization", "OAuth");
            }



        }

        try {

            int rc = hc.getResponseCode();

            InputStream in = hc.getInputStream();

            try {

                byte[] capture = new byte[in.available()];
                int amount = in.read(capture);
                s = new String(capture);

            } catch (Exception e) {

            } finally {
                in.close();
            }

            if (rc != HttpURLConnection.HTTP_OK) {

                if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
                    System.err.println("ERROR HTTP RETURN CODE IS: " + rc);
                    System.err.println("ERROR HTTP BODY IS: " + s);
                }

                throw new HttpException(HttpException.HTTP_OK_NOT_RECEIVED, rc);
            }

        } finally {
            hc.disconnect();
        }

        if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
            System.out.println("HTTP RESPONSE:");
            System.out.println(s);
        }
        
        return s;

    }

    public static String performHttpPost(String cs, YahooMessengerAuthentication authentication, String content)
        throws IOException, HttpException
    {

        String s = new String();

        URL url = new URL(cs);
        HttpURLConnection hc = (HttpURLConnection)url.openConnection();
        hc.setDoOutput(true);
        hc.setRequestMethod("POST");

        hc.setRequestProperty("User-Agent", "Stack/1.0.0.0");

        if (authentication == null) {
            throw new HttpException(HttpException.NO_AUTHENTICATION_GIVEN);
        }

        if (!authentication.isUsingOAuth())
            hc.setRequestProperty("Cookie", authentication.getCookie());
        else
            hc.setRequestProperty("Authorization", "OAuth");


        if (content != null) {

            hc.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            hc.setRequestProperty("Content-Length", ""+content.length());

            OutputStream os = hc.getOutputStream();
            os.write(content.getBytes());
            
        }

        if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
            System.out.println("HTTP POST SENT:");
            System.out.println(cs);
            System.out.println("HTTP BODY:");
            System.out.println(content);
        }

        try {

            int rc = hc.getResponseCode();


            InputStream in = hc.getInputStream();

            try {

                byte[] capture = new byte[in.available()];
                int amount = in.read(capture);
                s = new String(capture);

            } catch (Exception e) {

            } finally {
                in.close();
            }

            if (rc != HttpURLConnection.HTTP_OK) {

                if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
                    System.err.println("ERROR HTTP RETURN CODE IS: " + rc);
                    System.err.println("ERROR HTTP BODY IS: " + s);
                }

                throw new HttpException(HttpException.HTTP_OK_NOT_RECEIVED, rc);
            }

        } finally {
            hc.disconnect();
        }

        if (YahooMessengerConstants.debugHttpRequestResponse == 1) {
            System.out.println("HTTP RESPONSE:");
            System.out.println(s);
        }

        return s;
    }


}
