import com.clouway.nvuapp.adapter.PersistentQuestionRepository;
import com.clouway.nvuapp.adapter.PersistentQuestionnaireRepository;
import com.clouway.nvuapp.core.InMemoryQuestionRepository;
import com.clouway.nvuapp.core.QuestionRepository;
import com.clouway.nvuapp.core.QuestionnaireRepository;
import com.google.common.collect.ImmutableMap;
import core.PageHandler;
import core.PageRegistry;
import http.controllers.AdminHomePageHandler;
import http.controllers.AdminQuestionListHandler;
import http.controllers.HomeHandler;
import http.controllers.LoginHandler;
import http.controllers.QuestionListHandler;
import http.controllers.RegisterQuestionHandler;
import http.controllers.RegisterQuestionnaireHandler;
import http.controllers.TutorHandler;
import core.Question;
import http.controllers.AdminHomePageHandler;
import http.controllers.AdminQuestionListHandler;
import http.controllers.HomeHandler;
import http.controllers.QuestionListHandler;
import http.controllers.RegisterQuestionHandler;
import http.controllers.*;
import core.Question;
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
    final PersistentQuestionnaireRepository questionnaireRepository=new PersistentQuestionnaireRepository(dataStore);
    final PersistentQuestionRepository questionRepository=new PersistentQuestionRepository(dataStore);
    final PageRegistry registry = new ServerPageRegistry(
            ImmutableMap.<String, PageHandler>builder()
                    .put("/", new HomeHandler())
                    .put("/adminHome", new AdminHomePageHandler())
                    .put("/questions", new QuestionListHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/registerquestion", new RegisterQuestionHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/adminHome/registerquestion", new RegisterQuestionHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/adminHome/adminQuestions", new AdminQuestionListHandler("1234", new PersistentQuestionRepository(dataStore)))
                    .put("/registration", new TutorHandler(new PersistentTutorRepository(dataStore)))
                    .put("/adminHome/questionnaire", new RegisterQuestionnaireHandler(questionRepository, questionnaireRepository))
                    .put("/adminHome/new-questionnaire", new ShowRegisterQuestionnaireHandler(questionnaireRepository))
                    .put("/login", new LoginHandler(new PersistentTutorRepository(dataStore)))
                    .put("/adminHome/finishquestionnaire", new FinishRegisterQuestionnaireHandler(questionnaireRepository)).build(),
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
