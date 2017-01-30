package com.clouway.nvuapp.core;

import java.util.List;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public interface QuestionnaireRepository {
  void register(Questionnaire questionnaire);

  void update(Questionnaire questionnaire);

  List<Questionnaire> findAllQuestionnaires();

  Questionnaire getQuestionnaire(int id);

  Questionnaire getLastOrNewQuestionnaire();
}
