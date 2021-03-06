package http.servlet;

import core.PageHandler;
import core.PageRegistry;
import core.Request;
import core.Response;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class PageHandlerServletTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();


  @Test
  public void happyPath() throws Exception {
    final PageRegistry registry = context.mock(PageRegistry.class);
    final PageHandler pageHandler = context.mock(PageHandler.class);
    final HttpServletRequest req = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FakeServletOutputStream servletResponse = new FakeServletOutputStream();
    PageHandlerServlet pageHandlerServlet = new PageHandlerServlet(registry);

    context.checking(new Expectations() {{
      oneOf(req).getRequestURI();
      will(returnValue("/"));

      oneOf(registry).findHandler("/");
      will(returnValue(pageHandler));

      oneOf(pageHandler).handle(with(any(Request.class)));
      will(returnValue(new FakeResponse("Test text for template", HttpURLConnection.HTTP_OK)));

      oneOf(resp).setStatus(200);

      oneOf(resp).getOutputStream();
      will(returnValue(servletResponse));
    }});
    pageHandlerServlet.doGet(req, resp);
    servletResponse.assertHasValue("Test text for template");
  }

  @Test
  public void servletRedirectsWithStatus302() throws Exception {
    final PageRegistry registry = context.mock(PageRegistry.class);
    final PageHandler pageHandler = context.mock(PageHandler.class);
    final HttpServletRequest req = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    PageHandlerServlet pageHandlerServlet = new PageHandlerServlet(registry);

    context.checking(new Expectations() {{
      oneOf(req).getRequestURI();
      will(returnValue("/"));

      oneOf(registry).findHandler("/");
      will(returnValue(pageHandler));

      oneOf(pageHandler).handle(with(any(Request.class)));
      will(returnValue(new FakeResponse(HttpURLConnection.HTTP_MOVED_TEMP,
              Collections.singletonMap("Location", "/someOtherPage"))));

      oneOf(resp).sendRedirect("/someOtherPage");
    }});
    pageHandlerServlet.doGet(req, resp);
  }
}
