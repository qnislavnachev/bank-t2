package com.clouway.nvuapp.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import core.Question;
import core.Request;
import core.Response;
import http.controllers.AdminQuestionListHandler;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AdminQuestionListHandlerTest {
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void adminLoadAllQuestions() throws Exception {
        Request request = context.mock(Request.class);
        QuestionRepository repository = new InMemoryQuestionRepository(ImmutableMap.<String, List<Question>>of(
                "1234", Lists.newArrayList(
                        new Question("1234", "CAT1", 23, 1, 1, 1, "User-1234 How are you today?", "I feel Good", "I feel bad", "I feed unusual"),
                        new Question("1234", "CAT2", 23, 1, 1, 1, "User-1234 How you feel today?", "I feel Good", "I feel bad", "I feed unusual")
                ),
                "0987", Lists.newArrayList(
                        new Question("0987", "CAT1", 23, 1, 1, 1, "User-0987 How are you today?", "I feel Good", "I feel bad", "I feed unusual"),
                        new Question("0987", "CAT2", 23, 1, 1, 1, "User-0987 How you feel today?", "I feel Good", "I feel bad", "I feed unusual")
                )
        ));
        AdminQuestionListHandler questionListHandler = new AdminQuestionListHandler("admin", repository);
        Response response = questionListHandler.handle(request);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteStreams.copy(response.body(), out);

        String page = out.toString();
        System.out.println(page);
        assertThat(page, containsString("User-1234 How are you today?"));
        assertThat(page, containsString("User-1234 How you feel today?"));

        assertThat(page, containsString("User-0987 How are you today?"));
        assertThat(page, containsString("User-0987 How you feel today?"));
    }

    @Test
    public void NoQuestionsInRepository() throws Exception {
        Request request = context.mock(Request.class);
        QuestionRepository repository = new InMemoryQuestionRepository(Collections.<String, List<Question>>emptyMap());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        AdminQuestionListHandler questionListHandler = new AdminQuestionListHandler("admin", repository);
        Response response = questionListHandler.handle(request);
        ByteStreams.copy(response.body(), out);

        String page = out.toString();

        assertThat(page, containsString("Няма добавени въпроси до момента"));
    }

    @Test
    public void tutorIdIsNotAdmin() throws Exception {
        Request request = context.mock(Request.class);
        QuestionRepository repository = new InMemoryQuestionRepository(Collections.<String, List<Question>>emptyMap());

        AdminQuestionListHandler questionListHandler = new AdminQuestionListHandler("notAdmin", repository);
        Response response = questionListHandler.handle(request);

        String uri = response.headers().get("Location");

        assertThat(uri, equalTo("/home"));
    }
}
