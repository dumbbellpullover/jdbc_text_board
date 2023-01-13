package com.ukj.exam.board.service;

import com.ukj.exam.board.Member;
import com.ukj.exam.board.dao.MemberDao;

import java.sql.Connection;

public class MemberService {
  private MemberDao memberDao;
  public MemberService(Connection conn) {
    memberDao = new MemberDao(conn);
  }

  public boolean isLoginIdDup(String loginId) {
    return memberDao.isLoginIdDup(loginId);
  }
  public boolean isLoginPwDup(String loginPw) { return memberDao.isLoginPwDup(loginPw); }

  public int join(String loginId, String loginPw, String name) {
    return memberDao.join(loginId, loginPw, name);
  }

  public Member getMemberLoginIdPw(String loginId, String loginPw) {
    return memberDao.getMemberLoginIdPw(loginId, loginPw);
  }
}
