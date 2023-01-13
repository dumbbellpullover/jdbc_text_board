package com.ukj.exam.board.controller;

import com.ukj.exam.board.Article;
import com.ukj.exam.board.Rq;
import com.ukj.exam.board.service.ArticleService;
import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController extends Controller {

  private ArticleService articleService;

  public ArticleController(Connection conn, Scanner sc, Rq rq) {
    super(sc, rq);
    articleService = new ArticleService(conn);
  }

  public void add() {
    System.out.println("\n== 게시물 등록 ==");
    System.out.printf("제목: ");
    String title = sc.nextLine();
    System.out.printf("내용: ");
    String body = sc.nextLine();

    int id = articleService.add(title, body);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
  }

  public void showList() {
    List<Article> articles = articleService.getArticles();

    System.out.println("\n== 게시물 리스트 ==");

    if (articles.isEmpty()) {
      System.out.println("게시물이 없습니다.");
      return;
    }

    System.out.println("번호 / 제목");

    for (Article article : articles) {
      System.out.printf("% 2d / %s\n", article.id, article.title);
    }

  }

  public void showDetail() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
      return;
    }

    System.out.printf("\n== %d번 게시물 ==\n", id);
    System.out.printf("번호: %d\n", article.id);
    System.out.printf("작성 날짜: %s\n", article.regDate);
    System.out.printf("수정 날짜: %s\n", article.updateDate);
    System.out.printf("제목: %s\n", article.title);
    System.out.printf("내용: %s\n", article.body);

  }

  public void remove() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    boolean articleExists = articleService.articleExists(id);

    if (!articleExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

  }

  public void modify() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    System.out.print("새 제목: ");
    String title = sc.nextLine();
    System.out.print("새 내용: ");
    String body = sc.nextLine();

    articleService.update(id, title, body);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

  }
}
