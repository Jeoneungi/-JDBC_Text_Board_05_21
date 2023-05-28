package com.sbs.exam.board;

import com.sbs.exam.board.util.Util;

import java.util.Map;

public class Rq {
  private String url;
  private Map<String, String> params;
  private String urlPath;

  public Rq(String url) {
    this.url = url;
    params = Util.getParamsForUrl(this.url);
    urlPath = Util.getUrlPathFromUrl(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    return params.get(paramName);
  }
}