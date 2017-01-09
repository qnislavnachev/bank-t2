package com.clouway.nvuapp.adapter;

import persistent.dao.DataStore;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class TableManager {
  private final DataStore dataStore;

  public TableManager(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  public void truncateTable(String tableName) {
    String query = "truncate " + tableName;
    dataStore.update(query);
  }
}
