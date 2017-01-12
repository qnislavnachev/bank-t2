package persistent.adapter;

import core.ConnectionPool;
import core.Provider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {

  @Override
  public Connection get() {
    Connection connection = null;
    try {
      connection = ConnectionPool.get().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
