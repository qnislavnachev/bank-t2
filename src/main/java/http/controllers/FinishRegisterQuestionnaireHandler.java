package http.controllers;

import com.clouway.nvuapp.core.QuestionnaireRepository;
import com.google.common.collect.ImmutableMap;
import core.PageHandler;
import core.Questionnaire;
import core.Request;
import core.Response;
import http.servlet.RsFreemarker;

import java.util.Map;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class FinishRegisterQuestionnaireHandler implements PageHandler {
  private final QuestionnaireRepository repository;

  public FinishRegisterQuestionnaireHandler(QuestionnaireRepository repository) {
    this.repository = repository;
  }

  @Override
  public Response handle(Request req) {

    Questionnaire questionnaire = repository.getLastOrNewQuestionnaire();
    questionnaire.finish();
    Map<Integer, String> answers = questionnaire.getAnswers();
    repository.update(questionnaire);
    return new RsFreemarker("finishquestionnaire.html", ImmutableMap.of("values", questionnaire.getQuestions(),
            "answers", answers, "message", "Въпросник номер " + questionnaire.getID()));
  }
}
