package http.controllers;

import core.*;
import http.servlet.RsFreemarker;

import java.util.Collections;

public class AdminHomePageHandler implements SecuredHandler {
  @Override
  public Response handle(Request req, Tutor tutor) {
    return new RsFreemarker("adminHome.html", Collections.<String, Object>emptyMap());
  }
}