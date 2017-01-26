package com.clouway.nvuapp.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.clouway.nvuapp.adapter.http.controllers.AdminQuestionListHandler;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.clouway.nvuapp.core.ResponseReader.reader;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class AdminQuestionListHandlerTest {
  public final JUnitRuleMockery context = new JUnitRuleMockery();

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
    AdminQuestionListHandler questionListHandler = new AdminQuestionListHandler(repository);

    Response response = questionListHandler.handle(request, new Tutor("admin", ""));

    assertThat(reader().read(response), containsString("User-1234 How are you today?"));
    assertThat(reader().read(response), containsString("User-1234 How you feel today?"));

    assertThat(reader().read(response), containsString("User-0987 How are you today?"));
    assertThat(reader().read(response), containsString("User-0987 How you feel today?"));
  }

  @Test
  public void noQuestionsInRepository() throws Exception {
    Request request = context.mock(Request.class);
    QuestionRepository repository = new InMemoryQuestionRepository(Collections.<String, List<Question>>emptyMap());
    AdminQuestionListHandler questionListHandler = new AdminQuestionListHandler(repository);

    Response response = questionListHandler.handle(request, new Tutor("admin", ""));

    assertThat(reader().read(response), containsString("Няма добавени въпроси до момента"));
  }
}