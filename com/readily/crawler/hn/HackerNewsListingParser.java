package com.readily.crawler.hn;

import java.util.Collection;
import java.util.LinkedList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HackerNewsListingParser {
  public HackerNewsListing parse(Document document) {
    HackerNewsListing listing = new HackerNewsListing();
    
    Collection<HackerNewsListingChild> children =
        new LinkedList<HackerNewsListingChild>();
    Elements elements = document.select("tbody").get(2).select("tr");
    for (int i = 0; (i + 2) < elements.size(); i += 3) {
      HackerNewsListingChild child = new HackerNewsListingChild();
      
      // i % 3 == 0 gives title rows
      Element top = elements.get(i);
      Elements links = top.select("a");
      String link = links.get(links.size() - 1).toString();
      child.setUrl(link.split("<a href=\"")[1].split("\">")[0]);
      child.setTitle(link.split("\">")[1].split("</a>")[0]);
      
      // i % 3 == 1 gives points, username, etc.
      Elements subtext = elements.get(i + 1).select(".subtext");
      if (subtext.size() > 0) {
        Element bottom = subtext.get(0);
        
        try {
          String points = bottom.select("span").toString();
          child.setPoints(
              Integer.parseInt(
                  points.split("\">")[1].split("</span>")[0].split("\\s+")[0]));
        } catch (Exception e) {
          continue;
        }
        
        try {
          String user = bottom.select("a").get(0).toString();
          child.setUser(user.split("\">")[1].split("</a>")[0]);
        } catch (Exception e) {
          continue;
        }
        
        try {
          int hours =
              Integer.parseInt(
                  bottom.toString().split("</a>")[1].split("\\s+")[1]);
          child.setCreatedAt(System.currentTimeMillis() - hours * 60 * 60);
        } catch (Exception e) {
          continue;
        }
        
        children.add(child);
      }
    }
    
    return listing
        .setChildren(children)
        .setMore(
            elements.get(elements.size() - 1).select("a").get(0).toString()
                .split("<a href=\"")[1]
                .split("\">")[0]
                .split("\\s+")[0]
                .replace("\"", "")
                .replace("/", ""));
  }
}
