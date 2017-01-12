package http.controllers;

import com.clouway.nvuapp.core.SessionsRepository;
import core.*;
import http.servlet.RsRedirect;
import http.servlet.RsWithCookies;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticatedHandler implements PageHandler {
    private final SessionsRepository sessions;
    private final SecuredHandler handler;

    public AuthenticatedHandler(SessionsRepository sessions, SecuredHandler handler) {
        this.sessions = sessions;
        this.handler = handler;
    }

    @Override
    public Response handle(Request req) {
        Cookie cookie = req.cookie("SID");
        if (cookie == null) {
            return new RsRedirect("/login");
        }
        Optional<Tutor> possibleTutor = sessions.findTutorBySessionId(cookie.getValue(), LocalDateTime.now().withNano(0));
        if (!possibleTutor.isPresent()) {
            cookie.setMaxAge(0);
            return new RsWithCookies(cookie, new RsRedirect("/login"));
        }
        return new RsWithCookies(cookie, handler.handle(req, possibleTutor.get()));
    }
}