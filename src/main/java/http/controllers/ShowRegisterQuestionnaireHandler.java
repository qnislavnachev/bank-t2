package http.controllers;

import com.clouway.nvuapp.core.QuestionnaireRepository;
import com.google.common.collect.ImmutableMap;
import core.*;
import http.servlet.RsFreemarker;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ShowRegisterQuestionnaireHandler implements SecuredHandler {
  private final QuestionnaireRepository repository;

  public ShowRegisterQuestionnaireHandler(QuestionnaireRepository repository) {
    this.repository = repository;
  }

  @Override
  public Response handle(Request req, Tutor tutor) {
    Questionnaire questionnaire = repository.getLastOrNewQuestionnaire();
      if (questionnaire.isEmpty()) {
        String message="Нов въпросник номер:"+questionnaire.getID();
        return new RsFreemarker("generateQuestionnaire.html", ImmutableMap.of("values", Collections.emptyList(),"message",message));
      }
    String message = "Има незавършен въпросник номер " +questionnaire.getID() + ", моля завършете първо него";
    List<Question> unfinishedQUestionnairyQuestions = questionnaire.getQuestions();
    return new RsFreemarker("generateQuestionnaire.html", ImmutableMap.of("values",unfinishedQUestionnairyQuestions, "message", message));
  }
}
