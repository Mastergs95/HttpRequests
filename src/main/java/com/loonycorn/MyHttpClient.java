package com.loonycorn;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MyHttpClient {
    public static void main(String[]args) throws IOException, InterruptedException, ExecutionException {

        List<String> list = new ArrayList<>();

        list.add("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
        list.add("https://swapi.dev/api/planets/3/");


        Map<String,CompletableFuture<HttpResponse<String>>> responses = new HashMap<>();
        HttpClient client = HttpClient.newHttpClient();

        for(String url : list){

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            System.out.println("Trying pass the response at: " + new Date().toString());
            CompletableFuture<HttpResponse<String>> future = null;
            future = client.sendAsync(req,HttpResponse.BodyHandlers.ofString());

            responses.put(url,future);
        }







        try{
            System.out.println("Sleeping...");
            Thread.sleep(8000);
            System.out.println("Waking up");
        }catch (InterruptedException e){
            e.printStackTrace();
        }



        for(Map.Entry<String,CompletableFuture<HttpResponse<String>>> entry:
        responses.entrySet()){

            String responseBody = entry.getValue().thenApply(HttpResponse::body).get();
            System.out.println("\n##### URL: " + entry.getKey());

            JSONObject jsonObject = new JSONObject(responseBody);
            System.out.println("JSON:\n" + jsonObject.toString(4));
        }




      /*  HttpResponse<String> resp = client.send(req,HttpResponse.BodyHandlers.ofString());
        System.out.println("Status code:" + resp.statusCode());
        System.out.println("URI: " + resp.uri());


        HttpResponse<String> stringHttpResponse=resp.previousResponse().get();
        System.out.println(stringHttpResponse.statusCode());
        System.out.println(stringHttpResponse.uri());*/

    }
}
