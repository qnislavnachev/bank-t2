import com.clouway.nvuapp.adapter.PersistentQuestionRepository;
import com.clouway.nvuapp.adapter.PersistentQuestionnaireRepository;
import com.clouway.nvuapp.core.SessionsRepository;
import com.google.common.collect.ImmutableMap;
import core.SessionCleanerThread;
import core.PageHandler;
import core.PageRegistry;
import http.controllers.*;
import http.servlet.PageHandlerServlet;
import http.servlet.ResourceServlet;
import http.servlet.ServerPageRegistry;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import persistent.adapter.ConnectionProvider;
import persistent.adapter.PersistentSessionRepository;
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
    final PersistentQuestionnaireRepository questionnaireRepository=new PersistentQuestionnaireRepository(dataStore);
    final PersistentQuestionRepository questionRepository=new PersistentQuestionRepository(dataStore);
    final SessionsRepository sessions = new PersistentSessionRepository(dataStore, 60);
    final SessionCleanerThread sessionCleaner = new SessionCleanerThread(sessions);
    final PageRegistry registry = new ServerPageRegistry(
            ImmutableMap.<String, PageHandler>builder()
                    .put("/", new AuthenticatedHandler(sessions, new HomeHandler()))
                    .put("/adminHome", new AdminAuthenticationHandler(sessions, new AdminHomePageHandler()))
                    .put("/questions", new AuthenticatedHandler(sessions, new QuestionListHandler(new PersistentQuestionRepository(dataStore))))
                    .put("/registerquestion", new AuthenticatedHandler(sessions, new RegisterQuestionHandler(new PersistentQuestionRepository(dataStore))))
                    .put("/adminHome/registerquestion", new AdminAuthenticationHandler(sessions, new RegisterQuestionHandler(new PersistentQuestionRepository(dataStore))))
                    .put("/adminHome/adminQuestions", new AdminAuthenticationHandler(sessions, new AdminQuestionListHandler(new PersistentQuestionRepository(dataStore))))
                    .put("/registration", new TutorHandler(new PersistentTutorRepository(dataStore)))
                    .put("/adminHome/questionnaire", new AdminAuthenticationHandler(sessions, new RegisterQuestionnaireHandler(questionRepository, questionnaireRepository)))
                    .put("/adminHome/new-questionnaire", new AdminAuthenticationHandler(sessions, new ShowRegisterQuestionnaireHandler(questionnaireRepository)))
                    .put("/login", new LoginHandler(sessions, new PersistentTutorRepository(dataStore)))
                    .put("/adminHome/finishquestionnaire", new AdminAuthenticationHandler(sessions, new FinishRegisterQuestionnaireHandler(questionnaireRepository)))
                    .build(),
            new AuthenticatedHandler(sessions, new HomeHandler())
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
      sessionCleaner.start();
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
