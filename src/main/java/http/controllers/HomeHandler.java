package http.controllers;

import core.*;
import http.servlet.RsFreemarker;

import java.util.Collections;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class HomeHandler implements SecuredHandler {

  @Override
  public Response handle(Request req, Tutor tutor) {
    return new RsFreemarker("home.html",Collections.<String,Object>emptyMap());
  }
}
