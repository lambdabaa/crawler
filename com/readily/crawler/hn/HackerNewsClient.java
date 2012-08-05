package com.readily.crawler.hn;

import java.io.IOException;

import org.jsoup.Jsoup;


public class HackerNewsClient {
  private static final String HACKER_NEWS_URL = "http://news.ycombinator.com/";
  
  private static HackerNewsClient inst;
  
  private HackerNewsListingParser parser;
  
  public static HackerNewsClient inst() {
    if (inst == null) {
      inst = new HackerNewsClient();
    }
    
    return inst;
  }
  
  public HackerNewsClient() {
    parser = new HackerNewsListingParser();
  }
  
  public HackerNewsListing getListing(String path) throws IOException {
    StringBuilder urlString = new StringBuilder();
    urlString.append(HACKER_NEWS_URL);
    urlString.append(path);
    return parser.parse(Jsoup.connect(urlString.toString()).get());
  }
}
