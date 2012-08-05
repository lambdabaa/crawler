package com.readily.crawler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class RedditCommentsCrawler {
  private static final String ARTICLE_COLLECTION = "article";
  private static final String COMMENT_COLLECTION = "comment";
  private static final float MAX_REQUESTS_PER_SECOND = 0.5f;
  
  private Mongo mongo;
  private DB db;
  private DBCollection articles;
  private DBCollection comments;
  
  public RedditCommentsCrawler() {
    // Make a database connection
    try {
      mongo = new Mongo("localhost");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    db = mongo.getDB("article");
    articles = db.getCollection(ARTICLE_COLLECTION);
  }
  
  public static void main(String[] args) {
  }
}
