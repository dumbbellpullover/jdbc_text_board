package com.ukj.exam.board;

import com.ukj.exam.board.controller.ArticleController;
import com.ukj.exam.board.controller.MemberController;

import java.sql.*;
import java.util.*;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    String cmd;

    Connection conn = null;

    while (true) {
      System.out.printf("\n명령어 > ");
      cmd = sc.nextLine().trim();
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

        // DB 연결 중

        // 쿼리
        action(sc, cmd, conn, rq);

      } catch (SQLException e) {
        System.err.println("예외: DB에 연결할 수 없습니다.");
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

    }

  }

  private void action(Scanner sc, String cmd, Connection conn, Rq rq) {
    ArticleController articleController = new ArticleController(conn, sc, rq);
    MemberController memberController = new MemberController(conn, sc);

    if (rq.getUrlPath().equals("/usr/member/join")) {
      memberController.join();
    }
    else if (rq.getUrlPath().equals("/usr/member/login")) {
      memberController.login();
    }
    else if (rq.getUrlPath().equals("/usr/article/list")) { // 게시물 리스트
      articleController.showList();
    }
    else if (rq.getUrlPath().equals("/usr/article/detail")) { // 게시물 상세 보기
      articleController.showDetail();
    }
    else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 생성
      articleController.add();
    }
    else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.modify();
    }
    else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.remove();
    }
    else if (cmd.equals("exit")) {
      System.out.println("시스템 종료");
      System.exit(0); // 프로그램 즉시 종료

    } else {
      System.out.println("잘못된 명령어입니다.");
    }

  }

}
