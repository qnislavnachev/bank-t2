package http.servlet;

import core.Response;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class RsRedirectTest {

  @Test
  public void happyPath() throws Exception {
    Response rsRedirect = new RsRedirect("/someurl");
    assertThat(rsRedirect.status(), is(302));
    assertThat(rsRedirect.headers().get("Location"),is("/someurl"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void redirectUrlIsNull() throws Exception {
    new RsRedirect(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void redirectUrlIsEmpty() throws Exception {
    new RsRedirect("");
  }
}