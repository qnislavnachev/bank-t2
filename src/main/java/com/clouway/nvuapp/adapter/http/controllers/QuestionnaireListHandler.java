package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.RsFreemarker;
import com.clouway.nvuapp.core.*;

import java.util.Collections;
import java.util.List;

public class QuestionnaireListHandler implements SecuredHandler {
    private QuestionnaireRepository repository;

    public QuestionnaireListHandler(QuestionnaireRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response handle(Request request, Tutor tutor) {
        List<Questionnaire> questionnaireList = repository.findAllQuestionnaires();
        return new RsFreemarker("questionnaires.html",
                Collections.singletonMap("questionnaireList", questionnaireList));
    }
}