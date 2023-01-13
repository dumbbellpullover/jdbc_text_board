package com.ukj.exam.board.dao;

import com.ukj.exam.board.Member;
import com.ukj.exam.board.container.Container;
import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.util.Map;

public class MemberDao {


  public boolean isLoginIdDup(String loginId) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM member");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public boolean isLoginPwDup(String loginPw) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM member");
    sql.append("WHERE loginPw = ?", loginPw);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public int join(String loginId, String loginPw, String name) {

    SecSql sql = new SecSql();
    sql.append("INSERT INTO member");
    sql.append("(regDate, updateDate, loginId, loginPw, name) VALUES");
    sql.append("(NOW(), NOW(), ?, ?, ?)", loginId, loginPw, name);

    return DBUtil.insert(Container.conn, sql);
  }

  public Member getMemberLoginIdPw(String loginId, String loginPw) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM member");
    sql.append("WHERE loginId = ? AND loginPw = ?", loginId, loginPw);

    Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

    return new Member(memberMap);
  }
}
