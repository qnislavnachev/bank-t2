package persistent.adapter;

import com.clouway.nvuapp.core.TutorRepository;
import core.Tutor;
import persistent.dao.DataStore;

import java.util.List;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class PersistentTutorRepository implements TutorRepository {
  private DataStore dataStore;

  public PersistentTutorRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void register(Tutor tutor) {
    String query = "INSERT INTO TUTORS VALUES (?,?)";
    dataStore.update(query, tutor.tutorId, tutor.password);
  }

  public List<Tutor> findTutor(String id) {
    String fetchId = "SELECT * FROM TUTORS WHERE TUTOR_ID='" + id + "'";
    return dataStore.fetchRows(fetchId, resultSet -> (new Tutor(resultSet.getString(1), resultSet.getString(2))));
  }
}
