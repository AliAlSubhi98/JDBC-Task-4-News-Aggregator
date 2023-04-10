package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class APIConsumer {

    public static void main(String[] args) {
        String apiUrl = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=sq0oZI0Mf0YtP5ZnkYJNFcSUFm8mlhXR";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();

            while ((output = br.readLine()) != null) {
                json.append(output);
            }

            conn.disconnect();

            Gson gson = new Gson();
            Article myArticle = gson.fromJson(json.toString(), Article.class);

            // Use myObj for further processing
            System.out.println(myArticle.status);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

