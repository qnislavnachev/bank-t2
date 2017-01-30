package com.clouway.nvuapp.adapter.persistence;

import com.clouway.nvuapp.core.QuestionnaireRepository;
import com.clouway.nvuapp.core.Question;
import com.clouway.nvuapp.core.Questionnaire;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.clouway.nvuapp.adapter.persistence.dao.DataStore;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.clouway.nvuapp.core.CustomMatchers.containsObj;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class PersistentQuestionnaireRepositoryTest {
  DataStore dataStore = new DataStore(new ConnectionProvider());
  QuestionnaireRepository repository = new PersistentQuestionnaireRepository(dataStore);
  TableManager tableManager = new TableManager(dataStore);

  @Before
  public void setUp() throws Exception {
    tableManager.truncateTable("QUESTIONNAIRES");
  }

  @After
  public void tearDown() throws Exception {
    tableManager.truncateTable("QUESTIONNAIRES");
  }

  @Test
  public void happyPath() throws Exception {
    Questionnaire questionnaire = new Questionnaire(1);
    repository.register(questionnaire);
    assertThat(questionnaire, is(repository.getLastOrNewQuestionnaire()));
  }

  @Test
  public void updateingQuestionnaire() throws Exception {
    Questionnaire questionnaire = new Questionnaire(1);
    repository.register(questionnaire);
    questionnaire.addQuestions(new LinkedList<Question>() {{
      add(new Question("admin", "A1", 1, 2, 3, 4, "q", "a", "b", "c"));
    }});
    repository.update(questionnaire);
    assertThat(questionnaire,is(repository.getLastOrNewQuestionnaire()));
  }

  @Test
  public void getAllQuestionnaires() throws Exception {
      Questionnaire questionnaire1 = new Questionnaire(1);
      Questionnaire questionnaire2 = new Questionnaire(1);
      Questionnaire questionnaire3 = new Questionnaire(1);

      repository.register(questionnaire1);
      repository.register(questionnaire2);
      repository.register(questionnaire3);

      List<Questionnaire> questionnaireList = repository.findAllQuestionnaires();

      assertThat(questionnaireList, containsObj(questionnaire1));
      assertThat(questionnaireList, containsObj(questionnaire2));
      assertThat(questionnaireList, containsObj(questionnaire3));
  }

  @Test
  public void findQuestionnaire() throws Exception {
    Questionnaire questionnaire1 = new Questionnaire(1);
    Questionnaire questionnaire2 = new Questionnaire(2);
    Questionnaire questionnaire3 = new Questionnaire(3);

    repository.register(questionnaire1);
    repository.register(questionnaire2);
    repository.register(questionnaire3);

    Questionnaire expectedQuestionnaire = repository.getQuestionnaire(1);

    assertThat(expectedQuestionnaire, is(questionnaire1));
  }

  @Test
  public void findUnknownQuestionnaire() throws Exception {
    Questionnaire questionnaire1 = new Questionnaire(1);

    repository.register(questionnaire1);

    Questionnaire nonExistingQuestionnaire = repository.getQuestionnaire(5);

    assertThat(nonExistingQuestionnaire, is(nullValue()));
  }
}