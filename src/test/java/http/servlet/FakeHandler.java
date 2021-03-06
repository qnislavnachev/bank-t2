package http.servlet;

import core.PageHandler;
import core.Request;
import core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class FakeHandler implements PageHandler {
  @Override
  public Response handle(Request req) {
    return new Response() {
      @Override
      public InputStream body() throws IOException {
        return null;
      }

      @Override
      public Map<String, String> headers() {
        return null;
      }

      @Override
      public int status() {
        return 0;
      }
    };
  }
}
