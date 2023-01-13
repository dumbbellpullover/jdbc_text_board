package com.ukj.exam.board.dao;

import com.ukj.exam.board.Article;
import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  private Connection conn;
  public ArticleDao(Connection conn) {
    this.conn = conn;
  }

  public int add(String title, String body) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append("(regDate, updateDate, title, body) VALUES");
    sql.append("(NOW(), NOW(), ?, ?)", title, body);

    return DBUtil.insert(conn, sql);
  }

  public boolean articleExists(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0 AS cnt FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(conn, sql);
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);
    Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

    if (articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void update(int id, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article SET");
    sql.append("updateDate = NOW(),");
    sql.append("title = ?,", title);
    sql.append("body = ?", body);
    sql.append("WHERE id = ?", id);

    DBUtil.update(conn, sql);
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();

    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }
}
