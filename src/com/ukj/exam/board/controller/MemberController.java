package com.ukj.exam.board.controller;

import com.ukj.exam.board.Member;
import com.ukj.exam.board.service.MemberService;

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

  public void login() {
    String loginId;
    String loginPw;
    int tryCount = 3;

    System.out.println("\n== 로그인 ==");

    while (true) {
      if (tryCount > 0) {
        System.out.printf("입력 가능 횟수: %d\n", tryCount);
      } else if (tryCount == 0) {
        System.out.println("입력 가능 횟수를 전부 소진하였습니다.");
        System.out.println("아이디와 비밀번호 확인 후 다시 시도해 주세요.");
        break;
      }

      System.out.print("ID: ");
      loginId = sc.nextLine();
      System.out.print("PW: ");
      loginPw = sc.nextLine();

      if (loginId.length() == 0 || loginPw.length() == 0) {
        System.out.println("로그인 아이디나 로그인 비밀번호를 입력해주세요.");
        tryCount--;
        continue;
      }

      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
      boolean isLoginPwDup = memberService.isLoginPwDup(loginPw);

      if (!isLoginIdDup || !isLoginPwDup) {
        System.out.println("일치하는 아이디나 비밀번호가 없습니다.");
        tryCount--;
        continue;
      }

      Member member = memberService.getMemberLoginIdPw(loginId, loginPw);

      System.out.printf("%s님 환영합니다!\n", member.getName());
      break;
    }
  }
}
