package com.ukj.exam.board.controller;

import com.ukj.exam.board.Rq;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn;
  protected Scanner sc;
  protected Rq rq;


  public Controller(Scanner sc) {
    this.sc = sc;
  }

  public Controller(Scanner sc, Rq rq) {
    this.sc = sc;
    this.rq = rq;
  }

}
