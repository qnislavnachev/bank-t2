package http.controllers;

import com.clouway.nvuapp.core.TutorRepository;
import core.PageHandler;
import core.Request;
import core.Response;
import core.Tutor;
import http.servlet.RsFreemarker;
import http.servlet.RsRedirect;

import java.util.Collections;
import java.util.List;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class LoginHandler implements PageHandler {
  public final TutorRepository repository;

  public LoginHandler(TutorRepository repository) {
    this.repository = repository;
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
      return new RsRedirect("/");
    }
    return new RsFreemarker("login.html", Collections.singletonMap("message", "Грешно ID или парола!"));
  }


  private boolean ifOneIsNullOrEmpty(String tutorID, String password) {
    return ((tutorID == null || tutorID.isEmpty()) || (password == null || password.isEmpty()));
  }

  private boolean ifBothAreNullOrEmpty(String tutorID, String password) {
    return ((tutorID == null || tutorID.isEmpty()) && (password == null || password.isEmpty()));
  }
}
