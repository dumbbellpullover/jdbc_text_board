package com.ukj.exam.board;

import java.util.Map;

public class Article {
  public int id;
  public String regDate;
  public String updateDate;
  public int hit;
  public int memberId;
  public String title;
  public String body;
  public String extra__writer;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.hit = (int) articleMap.get("hit");
    this.memberId = (int) articleMap.get("memberId");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
    this.extra__writer = (String) articleMap.get("extra__writer") ;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", hit=" + hit +
        ", memberId=" + memberId +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        ", extra__writer='" + extra__writer + '\'' +
        '}';
  }
}
