package com.clouway.nvuapp.core;

import core.Tutor;

import java.util.List;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public interface TutorRepository {
  void register(Tutor tutor);

  List<Tutor> findTutor(String id);
}
