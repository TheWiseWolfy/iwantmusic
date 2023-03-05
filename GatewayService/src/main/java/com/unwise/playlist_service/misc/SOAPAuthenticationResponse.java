package com.unwise.playlist_service.misc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SOAPAuthenticationResponse {
    @JacksonXmlProperty(localName = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private AuthenticateResponseBody body;

    public AuthenticateResponseBody getBody() {
        return body;
    }

   public static class AuthenticateResponseBody {
        @JacksonXmlProperty(localName = "authenticateResponse", namespace = "services.auth.soap")
        private AuthenticateResponse authenticateResponse;

        public AuthenticateResponse getAuthenticateResponse() {
            return authenticateResponse;
        }
    }

    public static class AuthenticateResponse {
        @JacksonXmlProperty(localName = "authenticateResult", namespace = "services.auth.soap")
        private String authenticateResult;

        public String getAuthenticateResult() {
            return authenticateResult;
        }
    }
}

