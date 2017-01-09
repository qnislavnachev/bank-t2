package com.clouway.nvuapp.core;

import core.Question;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InMemoryQuestionRepository implements QuestionRepository {

    private final Map<String, List<Question>> tutorToQuestionsListMap;

    public InMemoryQuestionRepository(Map<String, List<Question>> tutorToQuestionsListMap) {
        this.tutorToQuestionsListMap = tutorToQuestionsListMap;
    }

    @Override
    public List<Question> getQuestions(String tutorId) {
        if (tutorToQuestionsListMap.containsKey(tutorId)) {
            return tutorToQuestionsListMap.get(tutorId);
        }
        return Collections.emptyList();
    }

}