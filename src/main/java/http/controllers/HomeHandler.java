package http.controllers;

import core.PageHandler;
import core.Request;
import core.Response;
import http.servlet.RsFreemarker;

import java.util.Collections;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class HomeHandler implements PageHandler {

  @Override
  public Response handle(Request req) {
    return new RsFreemarker("home.html",Collections.<String,Object>emptyMap());
  }
}
