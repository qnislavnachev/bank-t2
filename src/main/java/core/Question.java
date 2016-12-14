package core;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Question {
  private final String tutorId;
  private final String category;
  private final Integer module;
  private final Integer subModule;
  private final Integer theme;
  private final Integer difficulty;
  private final String question;
  private final String answerA;
  private final String answerB;
  private final String answerC;

  public Question(String tutorId, String category, Integer mod, Integer subMod, Integer theme, Integer diff, String question, String answerA, String answerB, String answerC) {
    this.tutorId = tutorId;
    this.category = category;
    this.module = mod;
    this.subModule = subMod;
    this.theme = theme;
    this.difficulty = diff;
    this.question = question;
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
  }

  public String getTutorId() {
    return tutorId;
  }

  public String getCategory() {
    return category;
  }

  public Integer getModule() {
    return module;
  }

  public Integer getSubModule() {
    return subModule;
  }

  public Integer getTheme() {
    return theme;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public String getQuestion() {
    return question;
  }

  public String getAnswerA() {
    return answerA;
  }

  public String getAnswerB() {
    return answerB;
  }

  public String getAnswerC() {
    return answerC;
  }
}
