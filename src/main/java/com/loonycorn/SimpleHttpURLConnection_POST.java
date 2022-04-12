package com.loonycorn;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SimpleHttpURLConnection_POST {

    public static void main(String[] args) throws IOException {
        //SENDING DATA
        String text;
        StringBuffer content = new StringBuffer();

            URL url = new URL("https://swapi.dev/api/planets/3/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            conn.setDoOutput(true);

            String postData = "{'email':'alice@loonycorn.com'," +
                    "'firstName':'Alice','lastName':'Alison'}";

            try (OutputStream outputStream = conn.getOutputStream()) {

                byte[] postBytes = postData.getBytes("utf-8");
                outputStream.write(postBytes, 0, postBytes.length);

                try (BufferedReader read = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {

                    StringBuilder responseText = new StringBuilder();

                    while ((text = read.readLine()) != null) {
                        responseText.append(text.trim());
                    }

                    if (conn.getHeaderField("Content-Type").contains("json")) {
                        JSONObject jsonObject = new JSONObject(responseText.toString());
                        System.out.println("JSON:\n" + jsonObject.toString(4));
                    } else {
                        System.out.println(responseText.toString());
                    }
                }

                conn.disconnect();


            } catch (MalformedURLException murlx) {
                murlx.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


