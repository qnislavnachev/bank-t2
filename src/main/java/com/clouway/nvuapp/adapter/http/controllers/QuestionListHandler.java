package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.RsFreemarker;
import com.clouway.nvuapp.core.*;

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