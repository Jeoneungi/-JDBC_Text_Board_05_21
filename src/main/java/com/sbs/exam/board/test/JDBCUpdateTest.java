package com.sbs.exam.board.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUpdateTest {
  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement pstat = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      conn = DriverManager.getConnection(url, "root", "");

      String sql = "UPDATE article";
      sql += " SET updateDate = NOW()";
      sql += ", title = '제목수정'";
      sql += ", content = '내용수정'";
      sql += " WHERE id = 1";

      pstat = conn.prepareStatement(sql);
      int affectRows = pstat.executeUpdate();

      System.out.println("affectRows : " + affectRows);
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
  }
}