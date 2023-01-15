package com.ukj.exam.board.controller;

import com.ukj.exam.board.Article;
import com.ukj.exam.board.Rq;
import com.ukj.exam.board.container.Container;
import com.ukj.exam.board.service.ArticleService;

import java.util.List;

public class ArticleController extends Controller {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }


  public void add() {
    if ( !Container.session.isLogged() ) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int memberId = Container.session.loggedMemberId;

    System.out.println("\n== 게시물 등록 ==");
    System.out.printf("제목: ");
    String title = Container.scanner.nextLine();
    System.out.printf("내용: ");
    String body = Container.scanner.nextLine();

    int id = articleService.add(memberId, title, body);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
  }

  public void showList() {
    List<Article> articles = articleService.getArticles();

    System.out.println("\n== 게시물 리스트 ==");

    if (articles.isEmpty()) {
      System.out.println("게시물이 없습니다.");
      return;
    }

    System.out.println("번호 / 작성날짜 / 제목 / 작성자");

    for (Article article : articles) {
      System.out.printf("% 2d / %s / %s / %s\n", article.id, article.updateDate, article.title, article.extra__writer);
    }

  }

  public void showDetail(Rq rq) {
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

    articleService.increaseHit(id);

    System.out.printf("\n== %d번 게시물 ==\n", id);
    System.out.printf("번호: %d\n", article.id);
    System.out.printf("작성 날짜: %s\n", article.regDate);
    System.out.printf("수정 날짜: %s\n", article.updateDate);
    System.out.printf("조회수: %d\n", article.hit);
    System.out.printf("작성자: %s\n", article.extra__writer);
    System.out.printf("제목: %s\n", article.title);
    System.out.printf("내용: %s\n", article.body);

  }

  public void remove(Rq rq) {
    if ( !Container.session.isLogged() ) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);
    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }
    if ( Container.session.loggedMemberId != article.memberId ) {
      System.out.println("게시물을 삭제할 수 있는 권한이 없습니다.");
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

  public void modify(Rq rq) {
    if ( !Container.session.isLogged() ) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);
    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }
    if ( Container.session.loggedMemberId != article.memberId ) {
      System.out.println("게시물을 수정할 수 있는 권한이 없습니다.");
      return;
    }

    System.out.print("새 제목: ");
    String title = Container.scanner.nextLine();
    System.out.print("새 내용: ");
    String body = Container.scanner.nextLine();

    articleService.update(id, title, body);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

  }
}
