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
        removeUnfinishedQuestionnaires(questionnaireList);
        return new RsFreemarker("questionnaires.html",
                Collections.singletonMap("questionnaireList", questionnaireList));
    }

    /**
     * this is a method that removes unfinished questionnaires from the list of
     * questionnaires which user is able to see, note that the list can contain
     * just 1 questionnaires that has no answers
     */
    private void removeUnfinishedQuestionnaires(List<Questionnaire> questionnaires) {
        for (Questionnaire each : questionnaires) {
            if (each.noAnswers()) {
                questionnaires.remove(each);
            }
        }
    }
}