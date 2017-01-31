package com.clouway.nvuapp.adapter.http.controllers;

import com.clouway.nvuapp.adapter.http.servlet.FakeResponse;
import com.clouway.nvuapp.core.PageHandler;
import com.clouway.nvuapp.core.Request;
import com.clouway.nvuapp.core.Response;
import com.google.common.collect.ImmutableMap;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static com.clouway.nvuapp.core.CustomMatchers.isRedirectingTo;
import static org.junit.Assert.assertThat;

public class AvailableSessionHandlerTest {
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void tutorHasNoSession() throws Exception {
        final Request request = context.mock(Request.class);
        final PageHandler pageHandler = context.mock(PageHandler.class);
        Response fakeResponse = new FakeResponse("text", 200, ImmutableMap.of("Location", "someUrl"));

        context.checking(new Expectations(){{
            oneOf(request).cookie("SID");
            will(returnValue(null));

            oneOf(pageHandler).handle(request);
            will(returnValue(fakeResponse));

        }});

        AvailableSessionHandler handler = new AvailableSessionHandler(pageHandler);
        Response response = handler.handle(request);

        assertThat(response, isRedirectingTo("someUrl"));
    }

    @Test
    public void tutorGotSession() throws Exception {
        final Request request = context.mock(Request.class);
        final PageHandler pageHandler = context.mock(PageHandler.class);
        Response fakeResponse = new FakeResponse("text", 200, ImmutableMap.of("Location", "someUrl"));

        context.checking(new Expectations(){{
            oneOf(request).cookie("SID");
            will(returnValue(new Cookie("SID", "value")));

            oneOf(pageHandler).handle(request);
            will(returnValue(fakeResponse));

        }});

        AvailableSessionHandler handler = new AvailableSessionHandler(pageHandler);
        Response response = handler.handle(request);

        assertThat(response, isRedirectingTo("/"));
    }
}
