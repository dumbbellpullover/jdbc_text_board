package com.ukj.exam.board.session;

import com.ukj.exam.board.Member;
import com.ukj.exam.board.container.Container;

public class Session {
  public int loggedMemberId;
  public Member loggedMember;

  public Session() {
    this.loggedMemberId = -1;
  }

  public boolean isLogged() {
    return loggedMemberId != -1;
  }

  public void logout() {
    loggedMemberId = -1;
    loggedMember = null;
  }

  public void login(Member member) {
    loggedMemberId = member.getId();
    loggedMember = member;
  }
}
