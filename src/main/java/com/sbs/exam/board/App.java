package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private Article lastArticle;

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

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "root", "");

          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", content = \"" + content + "\"";

          pstat = conn.prepareStatement(sql);
          int affectRows = pstat.executeUpdate();

          System.out.println("affectRows : " + affectRows);
          System.out.println("sql : " + sql);
        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
          System.out.println("에러: " + e);
        } finally {
          try {
            if (conn != null && !conn.isClosed()) {
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        Article article = new Article(id, title, content);
        lastArticle = article;

        System.out.println("생선된 게시물 객체 : " + article);

        articles.add(article);

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      }
      else if(cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("번호 / 제목");

        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }

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
