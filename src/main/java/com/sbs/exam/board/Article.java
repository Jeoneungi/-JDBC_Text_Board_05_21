package com.sbs.exam.board;

import java.util.Map;

public class Article {
  int id;
  String regDate;
  String updateDate;
  String title;
  String content;

  /*
  public Article(int id, String regDate, String updateDate, String title, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.content = content;
  }
   */

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.content = (String) articleMap.get("content");
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}