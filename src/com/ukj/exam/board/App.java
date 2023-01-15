package com.ukj.exam.board;

import com.ukj.exam.board.container.Container;

import java.sql.*;

public class App {
  public void run() {

    Container.init();
    Connection conn = null;

    String cmd;
    while (true) {

      String promptName = "명령어";

      System.out.printf("\n%s > ", promptName);
      cmd = Container.scanner.nextLine().trim();
      Rq rq = new Rq(cmd);

      // DB 연결 시작
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.err.println("예외: MySQL 드라이브 클래스가 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "guest", "bemyguest");
        Container.conn = conn;

        // DB 연결 중

        // 쿼리
        action(rq, cmd);

      } catch (SQLException e) {
        System.err.println("예외: DB에 연결할 수 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;

      } finally {
        try {
          if (conn!= null && !conn.isClosed()) {
            conn.close();

          }

        } catch (SQLException e) {
          e.printStackTrace();

        }

      }

    }

  }

  private void action(Rq rq, String cmd) {

    if (rq.getUrlPath().equals("/usr/member/join")) {
      Container.memberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      Container.memberController.login();
    } else if (rq.getUrlPath().equals("/usr/member/logout")) {
      Container.memberController.logout();
    } else if (rq.getUrlPath().equals("/usr/member/whoami")) {
      Container.memberController.whoAmI();
    } else if (rq.getUrlPath().equals("/usr/article/list")) { // 게시물 리스트
      Container.articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) { // 게시물 상세 보기
      Container.articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 생성
      Container.articleController.add();
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      Container.articleController.modify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      Container.articleController.remove(rq);
    } else if (cmd.equals("exit")) {
      System.out.println("시스템 종료");
      System.exit(0); // 프로그램 즉시 종료

    } else {
      System.out.println("잘못된 명령어입니다.");
    }

  }

}
