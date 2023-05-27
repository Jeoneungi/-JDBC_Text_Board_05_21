package com.sbs.exam.board;

public class Article {
  int id;
  String regDate;
  String updateDate;
  String title;
  String content;


  public Article(int id, String title, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.content = content;
  }

  public Article(int id, String regDate, String updateDate, String title, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.content = content;
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


