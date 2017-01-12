package http.controllers;

import com.clouway.nvuapp.core.SessionsRepository;
import com.clouway.nvuapp.core.TutorRepository;
import core.*;
import http.servlet.RsFreemarker;
import http.servlet.RsRedirect;
import http.servlet.RsWithCookies;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class LoginHandler implements PageHandler {
  public final TutorRepository repository;
  public final SessionsRepository sessionsRepository;

  public LoginHandler(SessionsRepository sessionsRepository, TutorRepository repository) {
    this.repository = repository;
    this.sessionsRepository = sessionsRepository;
  }

  @Override
  public Response handle(Request req) {
    String tutorID = req.param("LoginID");
    String password = req.param("LoginPass");
    List<Tutor> tutors = repository.findTutor(tutorID);
    if (ifBothAreNullOrEmpty(tutorID, password)) {
      return new RsFreemarker("login.html", Collections.singletonMap("message", ""));
    }
    if (ifOneIsNullOrEmpty(tutorID, password)) {
      return new RsFreemarker("login.html", Collections.singletonMap("message", "Моля попълнете всичките полета!"));
    }
    if (!tutors.isEmpty() && tutors.get(0).password.equals(password)) {
       return startSession(forward(tutorID), tutorID, LocalDateTime.now().withNano(0));
    }
    return new RsFreemarker("login.html", Collections.singletonMap("message", "Грешно ID или парола!"));
  }


  private boolean ifOneIsNullOrEmpty(String tutorID, String password) {
    return ((tutorID == null || tutorID.isEmpty()) || (password == null || password.isEmpty()));
  }

  private boolean ifBothAreNullOrEmpty(String tutorID, String password) {
    return ((tutorID == null || tutorID.isEmpty()) && (password == null || password.isEmpty()));
  }

  private Response forward(String tutorId) {
    if ("admin".equals(tutorId)) {
      return new RsRedirect("/adminHome");
    }
    return new RsRedirect("/");
  }

  private Response startSession(Response response, String tutorId, LocalDateTime instantTime) {
    TutorSession session = sessionsRepository.register(tutorId, instantTime);
    Cookie cookie = new Cookie("SID", session.id);
    cookie.setMaxAge(session.lifeSpan);
    return new RsWithCookies(cookie, response);
  }
}
