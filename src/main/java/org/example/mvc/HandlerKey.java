package org.example.mvc;

import org.example.annotation.RequestMethod;

import java.util.Objects;

public class HandlerKey {
    private final RequestMethod RequestMethod;
    private final String uriPath;

    public HandlerKey(RequestMethod requestMethod, String uriPath) {
        this.RequestMethod = requestMethod;
        this.uriPath = uriPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return RequestMethod == that.RequestMethod && Objects.equals(uriPath, that.uriPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RequestMethod, uriPath);
    }
}
