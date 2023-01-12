package com.ukj.exam.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    int articleLastId = 0;
    List<Article> articles = new ArrayList<>();

    while (true) {
      System.out.printf("\n명령어 > ");
      String cmd = sc.nextLine();

      if (cmd.equals("list")) {
        System.out.println("\n== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물이 없습니다.");
          continue;
        }

        System.out.println("번호 / 제목");

        for (Article article : articles) {
          System.out.printf("% 2d / %s", article.id, article.title);
        }

      } else if (cmd.equals("add")) {
        System.out.println("\n== 게시물 생성 ==");
        System.out.printf("제목: ");
        String title = sc.nextLine();
        System.out.printf("내용: ");
        String body = sc.nextLine();
        int id = ++articleLastId;

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "guest", "bemyguest");

          // 쿼리 작성 부분

          String query = "insert into article (regDate, updateDate, title, body) \n" +
              "values (now(), now(), \"" + title + "\", \"" + body + "\");";

          pstat = conn.prepareStatement(query);
          int affectedRows = pstat.executeUpdate();

          System.out.println("affectedRows: " + affectedRows);

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");

        } catch (SQLException e) {
          System.out.println("에러: " + e);


        } finally { // Connection, PreparedStatement 객체는 garbage collector가 수거하지 않아, 직접 메모리해제를 해줘야 한다.
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

        Article article = new Article(id, title, body);
        articles.add(article);

        System.out.println("생성된 게시물 객체 : " + article);
        System.out.printf("%d번 게시물을 생성하였습니다.\n", article.id);

      } else if (cmd.equals("exit")) {
        System.out.println("시스템 종료");
        break;

      } else {
        System.out.println("잘못된 명령어입니다.");
      }


    }

  }

}
