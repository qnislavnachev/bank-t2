package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.RsRedirect;
import com.clouway.nvuapp.core.PageHandler;
import com.clouway.nvuapp.core.Request;
import com.clouway.nvuapp.core.Response;

import javax.servlet.http.Cookie;

public class AvailableSessionHandler implements PageHandler {
    private PageHandler handler;

    public AvailableSessionHandler(PageHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response handle(Request req) {
        Cookie cookie = req.cookie("SID");
        if (cookie != null) {
            return new RsRedirect("/");
        }
        return handler.handle(req);
    }
}
