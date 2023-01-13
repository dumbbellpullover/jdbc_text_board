package com.ukj.exam.board.dao;

import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.sql.Connection;

public class MemberDao {
  private Connection conn;
  public MemberDao(Connection conn) {
    this.conn = conn;
  }

  public boolean isLoginIdDup(String loginId) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM member");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public int join(String loginId, String loginPw, String name) {

    SecSql sql = new SecSql();
    sql.append("INSERT INTO member");
    sql.append("(regDate, updateDate, loginId, loginPw, name) VALUES");
    sql.append("(NOW(), NOW(), ?, ?, ?)", loginId, loginPw, name);

    return DBUtil.insert(conn, sql);
  }
}
