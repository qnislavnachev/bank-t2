package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.RsFreemarker;
import com.clouway.nvuapp.core.*;

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