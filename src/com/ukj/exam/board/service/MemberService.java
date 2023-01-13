package com.ukj.exam.board.service;

import com.ukj.exam.board.Member;
import com.ukj.exam.board.container.Container;
import com.ukj.exam.board.dao.MemberDao;


public class MemberService {

  private MemberDao memberDao;

  public MemberService() {
    this.memberDao = Container.memberDao;
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
