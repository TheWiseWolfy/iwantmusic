package com.unwise.playlist_service.misc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SOAPValidationResponse {
    @JacksonXmlProperty(localName = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private ValidateTokenResponseBody body;

    public ValidateTokenResponseBody getBody() {
        return body;
    }

    public static class ValidateTokenResponseBody {
        @JacksonXmlProperty(localName = "validate_tokenResponse", namespace = "services.auth.soap")
        private ValidateTokenResponse validateTokenResponse;

        public ValidateTokenResponse getValidateTokenResponse() {
            return validateTokenResponse;
        }
    }

    public static class ValidateTokenResponse {
        @JacksonXmlProperty(localName = "validate_tokenResult", namespace = "services.auth.soap")
        private ValidateTokenResult validateTokenResult;

        public ValidateTokenResult getValidateTokenResult() {
            return validateTokenResult;
        }
    }

    public static class ValidateTokenResult {
        @JacksonXmlProperty(localName = "response", namespace = "services.auth.soap")
        private Response reponse;

        public Response getReponse() {
            return reponse;
        }
    }

    public static class Response {
        @JacksonXmlProperty(localName = "valid")
        private boolean valid;
        @JacksonXmlProperty(localName = "id")
        private String id;
        @JacksonXmlProperty(localName = "role")
        private String role;

        public boolean isValid() {
            return valid;
        }

        public String getId() {
            return id;
        }

        public String getRole() {
            return role;
        }
    }
}
