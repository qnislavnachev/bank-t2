package http.controllers;

import core.PageHandler;
import core.Request;
import core.Response;
import http.servlet.RsFreemarker;

import java.util.Collections;

public class AdminHomePageHandler implements PageHandler {
    @Override
    public Response handle(Request req) {
        return new RsFreemarker("adminHome.html", Collections.<String, Object>emptyMap());
    }
}