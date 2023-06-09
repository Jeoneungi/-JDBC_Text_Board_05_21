package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      // DB 연결 시작
      Connection conn = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "root", "");

        // 로직에 실행부분
        doAction(rq, conn, sc);

      } catch (SQLException e) {
        System.out.println("예외 : MySQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다.");
        break;
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // DB 연결 끝
    }

    sc.close();
  }

  private void doAction(Rq rq, Connection conn, Scanner sc) {
    List<Article> articles = new ArrayList<>();

    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 등록 ==");
      System.out.printf("제목) ");
      String title = sc.nextLine();
      System.out.printf("내용) ");
      String content = sc.nextLine();

      SecSql sql = new SecSql();

      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", content = ?", content);

      int id = DBUtil.insert(conn, sql);

      System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {

      SecSql sql = new SecSql();

      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

      for(Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
      }

      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("번호 / 제목");

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();

      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsExists = DBUtil.selectRowIntValue(conn, sql) == 1;

      if(articleIsExists == false) {
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return;
      }

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String content = sc.nextLine();

      sql = new SecSql();

      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", content = ?", content);
      sql.append("WHERE id = ?", id);

      DBUtil.update(conn, sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();

      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsExists = DBUtil.selectRowIntValue(conn, sql) == 1;

      if(articleIsExists == false) {
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();

      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      DBUtil.delete(conn, sql);

      System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("/exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }

    return;
  }
}