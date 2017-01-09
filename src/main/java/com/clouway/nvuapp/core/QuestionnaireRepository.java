package com.clouway.nvuapp.core;

import core.Questionnaire;

import java.util.Optional;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public interface QuestionnaireRepository {
  void register(Questionnaire questionnaire);

  void update(Questionnaire questionnaire);

  Questionnaire getLastOrNewQuestionnaire();
}
