package http.controllers;

import com.clouway.nvuapp.core.QuestionRepository;
import core.*;
import http.servlet.RsFreemarker;

import java.util.Collections;

public class AdminQuestionListHandler implements SecuredHandler {
  private QuestionRepository repository;

  public AdminQuestionListHandler(QuestionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Response handle(Request req, Tutor tutor) {
      return new RsFreemarker("questionList.html", Collections.<String, Object>singletonMap("questionList", repository.getQuestions()));
  }
}