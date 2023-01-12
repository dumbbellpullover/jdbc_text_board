package com.ukj.exam.board;

import java.util.Map;

public class Rq {
  private String url;
  private Map<String, String> params;
  private String urlPath;

  Rq(String url) {
    this.url = url;
    this.urlPath = Util.getUrlPathFromUrl(url);
    this.params = Util.getUrlParamsFromUrl(url);
  }


  public void setCommand(String url) {
    urlPath = Util.getUrlPathFromUrl(url);
    params = Util.getUrlParamsFromUrl(url);
  }

  public int getIntParam(String paramName, int defaultValue) {

    if(!params.containsKey(paramName)) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    }
    catch( NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {

    if(!params.containsKey(paramName)) {
      return defaultValue;
    }

    try {
      return params.get(paramName);
    }
    catch( NumberFormatException e) {
      return defaultValue;
    }
  }

  public Map<String, String> getParams() {
    return this.params;
  }

  public String getUrlPath() {
    return this.urlPath;
  }

}
