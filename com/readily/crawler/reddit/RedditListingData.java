package com.readily.crawler.reddit;

public class RedditListingData {
  private RedditListingDataChild[] children;
  private String after;
  private String before;
  
  public RedditListingDataChild[] getChildren() {
    return children;
  }
  
  public String getAfter() {
    return after;
  }
  
  public String getBefore() {
    return before;
  }
}
