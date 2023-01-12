package com.ukj.exam.board;

import java.util.HashMap;
import java.util.Map;

public class Util {

  public static Map<String, String> getUrlParamsFromUrl(String url) {
    Map<String, String> queryParams = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return queryParams;
    }

    for (String queryString : urlBits[1].split("&")) {
      String[] params = queryString.split("=");

      if (params.length == 1) {
        continue;
      }

      queryParams.put(params[0], params[1]);
    }

    return queryParams;
  }

  public static String getUrlPathFromUrl(String url) {
    return url.split("\\?")[0];
  }

}
