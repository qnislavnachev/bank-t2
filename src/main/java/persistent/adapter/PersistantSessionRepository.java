package persistent.adapter;

import com.clouway.nvuapp.core.SessionsRepository;
import core.Tutor;
import persistent.dao.DataStore;

import java.util.List;
import java.util.Optional;

public class PersistantSessionRepository implements SessionsRepository {
    private DataStore dataStore;

    public PersistantSessionRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void register(String sessionId, String tutorId) {
        String query = "insert into SESSIONS VALUES(?, ?)";
        dataStore.update(query, sessionId, tutorId);
    }

    @Override
    public Optional<Tutor> findTutorBySessionId(String sessionId) {
        String query = "select * from SESSIONS where SID=" + sessionId;
        List<Tutor> tutors = dataStore.fetchRows(query, resultSet -> new Tutor(resultSet.getString(1), ""));
        if (tutors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tutors.get(0));
    }
}