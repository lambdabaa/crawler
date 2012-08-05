package com.readily.crawler.reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class RedditClient {
  private static final String REDDIT_NEW_URL = "http://www.reddit.com/new/";
  private static final String USER_AGENT = "i can has news u/garethaye";
  
  private static RedditClient inst;
  
  private Gson gson;
  
  public static RedditClient inst() {
    if (inst == null) {
      inst = new RedditClient();
    }
    
    return inst;
  }
  
  public RedditClient() {
    gson = new Gson();
  }
  
  public RedditListing getListing(Integer count, String after) throws IOException {
    StringBuilder urlString = new StringBuilder();
    urlString.append(REDDIT_NEW_URL);
    urlString.append(".json?");
    if (count != null) {
      urlString.append("count=").append(count);
    }
    if (after != null) {
      urlString.append("&after=").append(after);
    }
    
    URL url = new URL(urlString.toString());
    HttpURLConnection handle = (HttpURLConnection) url.openConnection();
    handle.setRequestMethod("GET");
    handle.setDoOutput(true);
    handle.addRequestProperty("User-Agent", USER_AGENT);
    handle.setReadTimeout(10000);
    handle.connect();
    
    BufferedReader in =
        new BufferedReader(new InputStreamReader(handle.getInputStream()));
    StringBuilder sb = new StringBuilder();
    for (String line = in.readLine(); line != null; line = in.readLine()) {
      sb.append(String.format("%s\n", line));
    }
    
    return gson.fromJson(sb.toString(), RedditListing.class);
  }
}
