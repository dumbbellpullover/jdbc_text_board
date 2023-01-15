package com.ukj.exam.board.dao;

import com.ukj.exam.board.Article;
import com.ukj.exam.board.container.Container;
import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {

  public int add(int memberId, String title, String body) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append("(regDate, updateDate, memberId, title, body) VALUES");
    sql.append("(NOW(), NOW(), ?, ?, ?)", memberId, title, body);

    return DBUtil.insert(Container.conn, sql);
  }

  public boolean articleExists(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0 AS cnt FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writer");
    sql.append("FROM (SELECT * FROM article WHERE article.id = ?) AS A", id);
    sql.append("JOIN (SELECT id, name FROM member) AS M;");
    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

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

    DBUtil.update(Container.conn, sql);
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();

    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writer");
    sql.append("FROM article AS A");
    sql.append("JOIN member AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY A.id DESC");

    List< Map<String, Object> > articleListMap = DBUtil.selectRows(Container.conn, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article SET");
    sql.append("hit  = hit + 1");
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }
}
