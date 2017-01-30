package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.core.SessionsRepository;
import com.clouway.nvuapp.core.PageHandler;
import com.clouway.nvuapp.core.Request;
import com.clouway.nvuapp.core.Response;
import com.clouway.nvuapp.adapter.http.servlet.RsRedirect;
import com.clouway.nvuapp.adapter.http.servlet.RsWithCookies;

import javax.servlet.http.Cookie;

public class LogoutHandler implements PageHandler {
    private SessionsRepository repository;

    public LogoutHandler(SessionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response handle(Request req) {
        Cookie cookie = req.cookie("SID");
        if (cookie == null) {
            return new RsRedirect("/login");
        }
        deleteSession(cookie, repository);
        return new RsWithCookies(cookie, new RsRedirect("/login"));
    }

    private void deleteSession(Cookie cookie, SessionsRepository repository) {
        repository.deleteSession(cookie.getValue());
        cookie.setMaxAge(0);
    }
}
