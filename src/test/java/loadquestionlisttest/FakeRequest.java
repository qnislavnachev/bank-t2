package loadquestionlisttest;

import core.Request;

import javax.servlet.http.Cookie;

public class FakeRequest implements Request {
    @Override
    public String param(String name) {
        return null;
    }

    @Override
    public Cookie cookie(String cookieName) {
        return null;
    }
}