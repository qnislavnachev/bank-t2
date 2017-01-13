package com.clouway.nvuapp.adapter;

import com.clouway.nvuapp.core.QuestionRepository;
import com.google.common.base.Optional;
import core.Question;
import persistent.dao.DataStore;

import java.util.Collections;
import java.util.List;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class PersistentQuestionRepository implements QuestionRepository {
  private DataStore datastore;

  public PersistentQuestionRepository(DataStore datastore) {
    this.datastore = datastore;
  }

  @Override
  public List<Question> getQuestions(String tutorId) {
    String query = "SELECT * FROM QUESTIONS" +
            " WHERE TUTOR_ID='" + tutorId + "'";
    List<Question> result = datastore.fetchRows(query, resultSet -> new Question(
            resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getInt(3),
            resultSet.getInt(4),
            resultSet.getInt(5),
            resultSet.getInt(6),
            resultSet.getString(7),
            resultSet.getString(8),
            resultSet.getString(9),
            resultSet.getString(10)));
    return result;
  }

  @Override
  public String register(Question question) {
    if (findQuestionMatching(question.getTutorId(),
            String.valueOf(question.getCategory()),
            String.valueOf(question.getModule()),
            String.valueOf(question.getSubModule()),
            String.valueOf(question.getTheme()),
            String.valueOf(question.getDifficulty()),
            question.getQuestion()).isPresent()) {
      return "Вече има такъв регистриран въпрос.";
    }
    String query = "INSERT INTO QUESTIONS VALUES (?,?,?,?,?,?,?,?,?,?)";
    datastore.update(query,
            question.getTutorId(), question.getCategory(),
            question.getModule(), question.getSubModule(),
            question.getTheme(), question.getDifficulty(),
            question.getQuestion(), question.getAnswerA(),
            question.getAnswerB(), question.getAnswerC());
    return "Въпросът е регистриран успешно.";
  }

  @Override
  public List<Question> getQuestions() {
    return Collections.emptyList();
  }

  @Override
  public Optional<Question> findQuestionMatching(String tutorId, String category, String modul, String subModul, String theme, String diff, String question) {
    String query = "SELECT * FROM QUESTIONS" +
            " WHERE TUTOR_ID='" + tutorId + "'" +
            " AND CATEGORY='" + category + "'" +
            " AND MODL=" + modul +
            " AND SUB_MOD=" + subModul +
            " AND THEME=" + theme +
            " AND DIF=" + diff +
            " AND QUESTION='" + question + "'";

    List<Question> result = datastore.fetchRows(query, resultSet -> new Question(
            resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getInt(3),
            resultSet.getInt(4),
            resultSet.getInt(5),
            resultSet.getInt(6),
            resultSet.getString(7),
            resultSet.getString(8),
            resultSet.getString(9),
            resultSet.getString(10)));

    if (result.isEmpty()) {
      return Optional.absent();
    }
    return Optional.of(result.get(0));
  }
}
