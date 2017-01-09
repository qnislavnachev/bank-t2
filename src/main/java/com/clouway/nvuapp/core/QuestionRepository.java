package com.clouway.nvuapp.core;

import core.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> getQuestions(String tutorId);

}