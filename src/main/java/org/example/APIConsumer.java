package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

public class APIConsumer {
    static ArrayList<Article> articleArray = new ArrayList<Article>();


    public static void getAPI() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the article to fetch the API");
        String search = sc.next();
        String apiUrl = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="+ search + "&api-key=sq0oZI0Mf0YtP5ZnkYJNFcSUFm8mlhXR";
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
            ArticleResponse articleResponse = gson.fromJson(json.toString(), ArticleResponse.class);

            // Use articleResponse for further processing
            for (Article article : articleResponse.response.docs) {

                System.out.println("Title: " + article.headline.main);
                System.out.println("Author: " + article.byline.original);
                System.out.println("Date: " + article.pub_date);
                System.out.println("Category: " + article.section_name);
                System.out.println("Content: " + article.lead_paragraph);
                System.out.println();

                articleArray.add(article);
            }




        }
        catch (RuntimeException e){
            System.out.println("API key is invalid");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAPIMostPopularArticles () {

        String apiUrl = "https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=sq0oZI0Mf0YtP5ZnkYJNFcSUFm8mlhXR";
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
            ArticleResponse articleResponse = gson.fromJson(json.toString(), ArticleResponse.class);

            // Use articleResponse for further processing
            for (mostpopular pop : articleResponse.results) {

                System.out.println("Title: " + pop.title);
                System.out.println("Author: " + pop.byline);
                System.out.println("Date: " + pop.published_date);
                System.out.println("Category: " + pop.section);
                System.out.println("Content: " + pop.abstractItem);
                System.out.println();

                // not saved in array
                //articleArray.add(article);
            }




        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

