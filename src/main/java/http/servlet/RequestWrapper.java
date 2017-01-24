package http.servlet;

import core.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class RequestWrapper implements Request {
  private HttpServletRequest request;

  public RequestWrapper(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public String param(String name) {
    return request.getParameter(name);
  }

  @Override
  public Cookie cookie(String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }

    for (Cookie each : cookies) {
      if (each.getName().equals(cookieName)) {
        return each;
      }
    }
    return null;
  }
}
