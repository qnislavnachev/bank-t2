package com.clouway.nvuapp.core;

import com.clouway.nvuapp.adapter.TableManager;
import core.Calendar;
import core.Request;
import core.Tutor;
import http.controllers.LogoutHandler;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Test;
import persistent.adapter.ConnectionProvider;
import persistent.adapter.PersistentSessionRepository;
import persistent.dao.DataStore;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static core.CustomMatchers.isDead;
import static core.CustomMatchers.isNotPresent;
import static org.junit.Assert.assertThat;

public class LogoutHandlerTest {
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private final DataStore dataStore = new DataStore(new ConnectionProvider());
    private final TableManager tableManager = new TableManager(dataStore);

    @Before
    public void setUp() throws Exception {
        tableManager.truncateTable("SESSIONS");
    }

    @Test
    public void onLogoutCookieAndSessionGoneDead() throws Exception {
        final Request request = context.mock(Request.class);
        Calendar calendar = new Calendar();

        SessionsRepository repository = new PersistentSessionRepository(dataStore, 10);
        LogoutHandler handler = new LogoutHandler(repository);
        repository.register("user", calendar.newDateTime(15, 5, 2016, 2, 30));
        Cookie cookie = new Cookie("SID", "someId");

        context.checking(new Expectations() {{
            oneOf(request).cookie("SID");
            will(returnValue(cookie));
        }});

        handler.handle(request);
        Optional<Tutor> tutor = repository.findTutorBySessionId(cookie.getValue(), calendar.newDateTime(15, 5, 2016, 2, 35));

        assertThat(tutor, isNotPresent());
        assertThat(cookie, isDead());
    }

    @Test
    public void logoutWhenSessionIsNotAvailable() throws Exception {
        final Request request = context.mock(Request.class);
        Calendar calendar = new Calendar();

        SessionsRepository repository = new PersistentSessionRepository(dataStore, 10);
        LogoutHandler handler = new LogoutHandler(repository);
        Cookie cookie = new Cookie("SID", "someId");

        context.checking(new Expectations() {{
            oneOf(request).cookie("SID");
            will(returnValue(cookie));
        }});

        handler.handle(request);
        Optional<Tutor> tutor = repository.findTutorBySessionId(cookie.getValue(), calendar.newDateTime(15, 5, 2016, 2, 35));

        assertThat(tutor, isNotPresent());
        assertThat(cookie, isDead());
    }
}
