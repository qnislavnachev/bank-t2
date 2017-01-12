package http.controllers;

import com.clouway.nvuapp.core.QuestionRepository;
import core.*;
import http.servlet.RsFreemarker;

import java.util.Collections;

public class QuestionListHandler implements SecuredHandler {
  private final QuestionRepository questionRepository;

  public QuestionListHandler(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public Response handle(Request req, Tutor tutor) {
    return new RsFreemarker(
            "questionList.html", Collections.<String, Object>singletonMap("questionList", questionRepository.getQuestions(tutor.tutorId)));
  }
}