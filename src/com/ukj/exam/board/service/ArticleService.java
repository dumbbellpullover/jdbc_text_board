package com.ukj.exam.board.service;

import com.ukj.exam.board.Article;
import com.ukj.exam.board.container.Container;
import com.ukj.exam.board.dao.ArticleDao;

import java.util.List;

public class ArticleService {

  private ArticleDao articleDao;

  public ArticleService() {
    this.articleDao = Container.articleDao;
  }

  public int add(int memberId, String title, String body) {
    return articleDao.add(memberId, title, body);
  }

  public boolean articleExists(int id) {
    return articleDao.articleExists(id);
  }

  public void delete(int id) {
    articleDao.delete(id);
  }

  public Article getArticleById(int id) {
    return articleDao.getArticleById(id);
  }

  public void update(int id, String title, String body) {
    articleDao.update(id, title, body);
  }

  public List<Article> getArticles(int page, int pageItemCount) {
    return Container.articleDao.getArticles(page, pageItemCount);
  }

  public void increaseHit(int id) {
    articleDao.increaseHit(id);
  }
}
