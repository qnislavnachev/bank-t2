package http.controllers;

import com.clouway.nvuapp.core.SessionsRepository;
import core.PageHandler;
import core.Request;
import core.Response;
import http.servlet.RsRedirect;
import http.servlet.RsWithCookies;

import javax.servlet.http.Cookie;

public class LogoutHandler implements PageHandler {
    private SessionsRepository repository;

    public LogoutHandler(SessionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response handle(Request req) {
        Cookie cookie = req.cookie("SID");
        repository.deleteSession(cookie.getValue());
        cookie.setMaxAge(0);
        return new RsWithCookies(cookie, new RsRedirect("/login"));
    }
}
