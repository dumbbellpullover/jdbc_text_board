package com.ukj.exam.board;

import java.util.Map;

public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private String name;

  public Member(Map<String, Object> memberMap) {
    this.id = (int) memberMap.get("id");
    this.regDate = (String) memberMap.get("regDate");
    this.updateDate = (String) memberMap.get("updateDate");
    this.loginId = (String) memberMap.get("loginId");
    this.loginPw = (String) memberMap.get("loginPw");
    this.name = (String) memberMap.get("name");
  }

  public String getName() {
    return this.name;
  }

  public int getId() { return this.id; }

  public String getLoginId() {
    return this.loginId;
  }
}
