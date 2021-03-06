package http.servlet;

import core.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class FakeResponse implements Response {
  private byte[] bytes;
  private Integer status;
  private Map<String,String> headers=new LinkedHashMap<>();

  public FakeResponse(String text, Integer status, Map<String, String> headers) {
    this.bytes = text.getBytes();
    this.status = status;
    this.headers = headers;
  }

  public FakeResponse(String body, Integer status){
    this.bytes = body.getBytes();
    this.status=status;
  }

  public FakeResponse(Integer status, Map<String, String> headers) {
    this.status = status;
    this.headers = headers;
  }

  @Override
  public InputStream body() throws IOException {
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public Map<String, String> headers() {
    return headers;
  }

  @Override
  public int status() {
    return status;
  }

  public void assertHasHeader(String header){
    assertTrue(headers.containsKey(header));
  }
}
