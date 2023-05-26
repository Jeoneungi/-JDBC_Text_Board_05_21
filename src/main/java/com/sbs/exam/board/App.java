package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    List<Article> articles = new ArrayList<>();
    int articleLastId = 0;

    while (true) {
      System.out.printf("명령어)");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목) ");
        String title = sc.nextLine();
        System.out.printf("내용) ");
        String content = sc.nextLine();

        int id = ++articleLastId;

        Article article = new Article(id, title, content);
        System.out.println("생선된 게시물 객체 : " + article);

        articles.add(article);

        System.out.printf("%d번 게시물이 등록되었습니다람쥐 .\n", article.id);

      }

      else if(cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");
      }
      else if(cmd.equals("system exit")) {
        System.out.println("프로그램 종료");
        break;
      }
      else {
        System.out.println("명령어를 확인해주세요");
      }
    }
    sc.close();
  }
}
