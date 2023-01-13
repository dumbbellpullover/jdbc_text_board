package com.ukj.exam.board.controller;

import com.ukj.exam.board.service.MemberService;
import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController extends Controller {

  private MemberService memberService;

  public MemberController(Connection conn, Scanner sc) {
    super(sc);
    memberService = new MemberService(conn);
  }
  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("\n== 회원 가입 ==");

    //아이디 입력
    while (true) {
      System.out.printf("로그인 아이디: ");
      loginId = sc.nextLine().trim();

      if (loginId.length() == 0) {
        System.out.println("아이디를 입력해주세요.");
        continue;
      }

      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      if(isLoginIdDup) {
        System.out.printf("이미 사용중인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }
    //비밀번호 입력
    while (true) {
      System.out.printf("로그인 비번: ");
      loginPw = sc.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("비밀번호를 입력해주세요.");
        continue;

      }

      boolean loginPwConfirmSame = true;

      while (true) {

        System.out.printf("로그인 비번 확인: ");
        loginPwConfirm = sc.nextLine().trim();

        if (!loginPw.equals(loginPwConfirm)) {
          loginPwConfirmSame = false;
          System.out.println("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.\n");
          break;

        }
        break;

      }
      if (loginPwConfirmSame) {
        break;

      }

    }
    //이름 입력
    while (true) {
      System.out.printf("이름: ");
      name = sc.nextLine();

      if (name.length() == 0) {
        System.out.println("이름을 입력해주세요.");
        continue;

      }

      break;
    }

    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("\n%s님이 가입되었습니다.\n", name);

  }

}
