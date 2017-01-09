package com.clouway.nvuapp.core;

import core.Question;

import java.util.*;

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

    @Override
    public List<Question> getQuestions() {
        List<Question> list = new LinkedList<>();
        for (String tutorId : tutorToQuestionsListMap.keySet()) {
            for (Question question : tutorToQuestionsListMap.get(tutorId)) {
                list.add(question);
            }
        }
        return list;
    }
}