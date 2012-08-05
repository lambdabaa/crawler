package com.readily.crawler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.readily.crawler.hn.HackerNewsClient;
import com.readily.crawler.hn.HackerNewsListing;
import com.readily.crawler.hn.HackerNewsListingChild;

public class HackerNewsCrawler {
  private static final String HN_DATABASE = "article";
  private static final String ARTICLE_COLLECTION = "hnarticles";
  private static final float MAX_REQUESTS_PER_SECOND = 0.25f;
  
  private Mongo mongo;
  private DB db;
  private DBCollection articles;
  
  public HackerNewsCrawler() {
    // Make a database connection
    try {
      mongo = new Mongo("localhost");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    db = mongo.getDB(HN_DATABASE);
    articles = db.getCollection(ARTICLE_COLLECTION);
  }
  
  public void start() {
    System.out.println("Starting to crawl Hacker News...");
    
    int count = 0;
    
    String path = "news";
    while (true) {
      HackerNewsListing listing = null;
      try {
        listing = HackerNewsClient.inst().getListing(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      Collection<DBObject> dbObjects = new LinkedList<DBObject>();
      for (HackerNewsListingChild child : listing.getChildren()) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", child.getUrl());
        map.put("title", child.getTitle());
        map.put("user", child.getUser());
        map.put("points", child.getPoints());
        map.put("createdAt", child.getCreatedAt());
        dbObjects.add(new BasicDBObject(map));
      }
      
      int num = dbObjects.size();
      
      articles.insert(dbObjects.toArray(new DBObject[num]));
      System.out.printf("Adding %d objects for a total of %d\n", num, count = count + num);
      
      path = listing.getMore();
      if (path == null) {
        break;
      }
      
      System.out.printf("New path = %s\n", path);
      
      try {
        Thread.sleep((long) (1000 / MAX_REQUESTS_PER_SECOND));
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
    
    System.out.println("Done");
  }
  
  
  public static void main(String[] args) {
    HackerNewsCrawler crawler = new HackerNewsCrawler();
    crawler.start();
  }
}
