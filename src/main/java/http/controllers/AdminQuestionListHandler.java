package http.controllers;

import com.clouway.nvuapp.core.QuestionRepository;
import core.PageHandler;
import core.Request;
import core.Response;
import http.servlet.RsFreemarker;
import http.servlet.RsRedirect;

import java.util.Collections;

public class AdminQuestionListHandler implements PageHandler {
    private String tutorId;
    private QuestionRepository repository;

    public AdminQuestionListHandler(String tutorId, QuestionRepository repository) {
        this.tutorId = tutorId;
        this.repository = repository;
    }

    @Override
    public Response handle(Request req) {
        if ("admin".equals(tutorId)) {
            return new RsFreemarker("questionList.html", Collections.<String, Object>singletonMap("questionList", repository.getQuestions()));
        }
        return new RsRedirect("/home");
    }
}