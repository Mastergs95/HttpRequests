package com.loonycorn;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class SimpleHttpURLConnection {

    public static void main(String[] args) throws IOException {

        //REQUEST DATA

        BufferedReader read;
        String text;
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL("https://swapi.dev/api/planets/3/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);

            int statusCode= connection.getResponseCode();

            System.out.println("The status is: " + statusCode);

            System.out.println("Return headers: \n" + connection.getHeaderFields());
            System.out.println("Content Size: \n" + connection.getHeaderField("Content-Length"));
            System.out.println("Content type: \n" + connection.getHeaderField("Content-Type"));


            System.out.println("\nThe response body: ");

            if(statusCode>=200 && statusCode<=299){

                read = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((text = read.readLine())!=null){
                    content.append(text);
                }

                read.close();
                System.out.println(content.toString());
            }
            else{
                System.out.println("The request failed: " + connection.getResponseMessage());
            }
            connection.disconnect();


        }catch (MalformedURLException murlx){
            murlx.printStackTrace();
        }catch (IOException iox){
            iox.printStackTrace();
        }

    }
}