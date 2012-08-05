package com.readily.crawler.hn;

public class HackerNewsListingChild {
  private String title;
  private String url;
  private Integer points;
  private String user;
  private Long createdAt;
  
  public String getTitle() {
    return title;
  }
  
  public HackerNewsListingChild setTitle(String title) {
    this.title = title;
    return this;
  }
  
  public String getUrl() {
    return url;
  }
  
  public HackerNewsListingChild setUrl(String url) {
    this.url = url;
    return this;
  }
  
  public Integer getPoints() {
    return points;
  }
  
  public HackerNewsListingChild setPoints(Integer points) {
    this.points = points;
    return this;
  }
  
  public String getUser() {
    return user;
  }
  
  public HackerNewsListingChild setUser(String user) {
    this.user = user;
    return this;
  }
  
  public Long getCreatedAt() {
    return createdAt;
  }
  
  public HackerNewsListingChild setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}
