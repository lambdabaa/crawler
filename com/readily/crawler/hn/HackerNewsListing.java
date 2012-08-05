package com.readily.crawler.hn;

import java.util.Collection;

public class HackerNewsListing {
  private Collection<HackerNewsListingChild> children;
  private String more;
  
  public Collection<HackerNewsListingChild> getChildren() {
    return children;
  }
  
  public HackerNewsListing setChildren(
      Collection<HackerNewsListingChild> children) {
    this.children = children;
    return this;
  }

  public String getMore() {
    return more;
  }

  public HackerNewsListing setMore(String more) {
    this.more = more;
    return this;
  }
}
