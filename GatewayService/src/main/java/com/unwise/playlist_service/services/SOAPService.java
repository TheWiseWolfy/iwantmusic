package com.unwise.playlist_service.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.unwise.playlist_service.controllers.GatewayController;
import com.unwise.playlist_service.misc.SOAPAuthenticationResponse;
import com.unwise.playlist_service.misc.SOAPValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@Service
public class SOAPService {

    Logger logger = LoggerFactory.getLogger(SOAPService.class);

    public Optional<String> isValidCredentials(String username, String password) {
        try{
            String requestSOAP =  String.format("""
                    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns0="services.auth.soap" xmlns:ns1="http://schemas.xmlsoap.org/soap/envelope/">
                    <SOAP-ENV:Header/>
                        <ns1:Body>
                            <ns0:authenticate>
                                <ns0:username>%s</ns0:username>
                                <ns0:password>%s</ns0:password>
                            </ns0:authenticate>
                        </ns1:Body>
                    </SOAP-ENV:Envelope>
                    """,username,password);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8070"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestSOAP))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //Here we read the response

            XmlMapper mapper = new XmlMapper();
            SOAPAuthenticationResponse parsedResponse = mapper.readValue(response.body(), SOAPAuthenticationResponse.class);
            String authenticateResult = parsedResponse.getBody().getAuthenticateResponse().getAuthenticateResult();

            return Optional.of(authenticateResult);
        }catch (Exception e){
            logger.error(e.toString());
        }
        return Optional.empty();
    }

    public Optional<SOAPValidationResponse.Response> isValidToken(String token){

        logger.info("We are validating the token " + token );
        try{
            String requestSOAP =  String.format("""
                    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns0="services.auth.soap" xmlns:ns1="http://schemas.xmlsoap.org/soap/envelope/">
                              <SOAP-ENV:Header/>
                                  <ns1:Body>
                                      <ns0:validate_token>
                                          <ns0:token>%s</ns0:token>
                                      </ns0:validate_token>
                                  </ns1:Body>
                              </SOAP-ENV:Envelope>
                    """,token);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8070"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestSOAP))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //Here we read the response

            XmlMapper xmlMapper = new XmlMapper();
            SOAPValidationResponse soapValidationResponse = xmlMapper.readValue(response.body(), SOAPValidationResponse.class);
            SOAPValidationResponse.Response validationResponse = soapValidationResponse.getBody().getValidateTokenResponse().getValidateTokenResult().getReponse();

            logger.info(String.format("We recovered info about the token: valid: %s, id: %s, role: %s",
                    validationResponse.isValid(), validationResponse.getId(), validationResponse.getRole()));

            if( validationResponse.isValid() ) {
                return Optional.of(validationResponse);
            }

        }catch (Exception e){
            logger.error(e.toString());
        }

        return Optional.empty();
    }

}
