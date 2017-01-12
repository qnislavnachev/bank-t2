package com.clouway.nvuapp.core;

import core.Tutor;

import java.util.Optional;

public interface SessionsRepository {

    void register(String sessionId, String tutorId);

    Optional<Tutor> findTutorBySessionId(String sessionId);
}
