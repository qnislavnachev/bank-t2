package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.RsFreemarker;
import com.clouway.nvuapp.adapter.http.servlet.RsRedirect;
import com.clouway.nvuapp.core.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewQuestionnaireHandler implements SecuredHandler {
    private QuestionnaireRepository repository;

    public ViewQuestionnaireHandler(QuestionnaireRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response handle(Request request, Tutor tutor) {
        Integer id = Integer.valueOf(request.param("id"));
        Questionnaire questionnaire = repository.getQuestionnaire(id);

        if (questionnaire == null) {
            return new RsRedirect("/adminHome");
        }
        return new RsFreemarker("showTest.html", getTemplates(questionnaire, id));
    }

    private Map<String, Object> getTemplates(Questionnaire questionnaire, int id) {
        return new LinkedHashMap<String, Object>(){{
            put("questions", questionnaire.getQuestions());
            put("num", id);
        }};
    }
}