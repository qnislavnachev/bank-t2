package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.FakeRequest;
import com.clouway.nvuapp.adapter.persistence.ConnectionProvider;
import com.clouway.nvuapp.adapter.persistence.PersistentQuestionnaireRepository;
import com.clouway.nvuapp.adapter.persistence.TableManager;
import com.clouway.nvuapp.adapter.persistence.dao.DataStore;
import com.clouway.nvuapp.core.*;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.clouway.nvuapp.core.CustomMatchers.isRedirectingTo;
import static com.clouway.nvuapp.core.ResponseReader.reader;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ViewQuestionnaireHandlerTest {
    private DataStore dataStore = new DataStore(new ConnectionProvider());
    private TableManager tableManager = new TableManager(dataStore);

    @Before
    public void setUp() throws Exception {
        tableManager.truncateTable("QUESTIONNAIRES");
    }

    @Test
    public void viewChosenTest() throws Exception {
        QuestionnaireRepository repository = new PersistentQuestionnaireRepository(dataStore);
        Request request = new FakeRequest(Collections.singletonMap("id", 1));

        Questionnaire questionnaire = new Questionnaire(1);
        questionnaire.addQuestions(Lists.newArrayList(new Question("4321", "A1", 1, 2, 3, 4, "myQuestion", "a", "b", "c")));

        repository.register(questionnaire);

        ViewQuestionnaireHandler handler = new ViewQuestionnaireHandler(repository);
        Response response = handler.handle(request, new Tutor("admin", ""));

        assertThat(reader().read(response), containsString("Въпросник № 1"));
        assertThat(reader().read(response), containsString("myQuestion"));
    }

    @Test
    public void viewUnknownTest() throws Exception {
        QuestionnaireRepository repository = new PersistentQuestionnaireRepository(dataStore);
        Request request = new FakeRequest(Collections.singletonMap("id", 5));

        repository.register(new Questionnaire(1));

        ViewQuestionnaireHandler handler = new ViewQuestionnaireHandler(repository);
        Response response = handler.handle(request, new Tutor("admin", ""));

        assertThat(response, isRedirectingTo("/adminHome"));
    }
}
