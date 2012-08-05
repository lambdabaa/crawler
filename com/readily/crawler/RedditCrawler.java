package com.readily.crawler;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.readily.crawler.reddit.RedditClient;
import com.readily.crawler.reddit.RedditListing;
import com.readily.crawler.reddit.RedditListingDataChild;

public class RedditCrawler {
  private static final String REDDIT_DATABASE = "article";
  private static final String ARTICLE_COLLECTION = "redditarticles";
  private static final float MAX_REQUESTS_PER_SECOND = 0.5f;
  
  private Mongo mongo;
  private DB db;
  private DBCollection articles;
  
  public RedditCrawler() {
    // Make a database connection
    try {
      mongo = new Mongo("localhost");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    db = mongo.getDB(REDDIT_DATABASE);
    articles = db.getCollection(ARTICLE_COLLECTION);
  }
  
  public void start() {
    System.out.println("Starting to crawl Reddit...");
    
    int count = 0;
    
    String after = null;
    while (true) {
      RedditListing listing = null;
      try {
        listing = RedditClient.inst().getListing(25, after);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      Collection<DBObject> dbObjects = new LinkedList<DBObject>();
      for (RedditListingDataChild child : listing.getData().getChildren()) {
        dbObjects.add(new BasicDBObject(child.getData()));
      }
      
      int num = dbObjects.size();
      
      articles.insert(dbObjects.toArray(new DBObject[num]));
      System.out.printf("Adding %d objects for a total of %d\n", num, count = count + num);
      
      after = listing.getData().getAfter();
      if (after == null) {
        break;
      }
      
      System.out.printf("New after = %s\n", after);
      
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
    RedditCrawler crawler = new RedditCrawler();
    crawler.start();
  }
}
