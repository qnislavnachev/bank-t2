import com.clouway.nvuapp.adapter.PersistentQuestionRepository;
import com.google.common.collect.ImmutableMap;
import core.PageHandler;
import core.PageRegistry;
import http.controllers.*;
import http.servlet.PageHandlerServlet;
import http.servlet.ResourceServlet;
import http.servlet.ServerPageRegistry;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import persistent.adapter.ConnectionProvider;
import persistent.adapter.PersistentTutorRepository;
import persistent.dao.DataStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class JettyMain {
  public static void main(String[] args) {
    final DataStore dataStore = new DataStore(new ConnectionProvider());
    final PageRegistry registry = new ServerPageRegistry(
            ImmutableMap.<String, PageHandler>builder()
                    .put("/", new HomeHandler())
                    .put("/adminHome", new AdminHomePageHandler())
                    .put("/questions", new QuestionListHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/registerquestion", new RegisterQuestionHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/adminQuestions", new AdminQuestionListHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/registration", new TutorHandler(new PersistentTutorRepository(dataStore))
                    ).build(),
           new HomeHandler()
    );

    Server server = new Server(8080);
    ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContext.setContextPath("/");
    servletContext.addEventListener(new ServletContextListener() {
      @Override
      public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addServlet("ResourceServlet", new ResourceServlet()).addMapping("/assets/*");
        servletContext.addServlet("pageHandlerServlet", new PageHandlerServlet(registry)).addMapping("/");
      }

      @Override
      public void contextDestroyed(ServletContextEvent servletContextEvent) {
      }
    });
    server.setHandler(servletContext);
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
