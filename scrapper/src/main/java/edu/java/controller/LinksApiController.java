package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.model.Request.AddLinkRequest;
import edu.java.model.Request.RemoveLinkRequest;
import edu.java.model.Response.LinkResponse;
import edu.java.model.Response.ListLinksResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
           date = "2024-02-29T10:09:42.512141887Z[GMT]")
@RestController
public class LinksApiController implements LinksApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksApiController.class);
    private final String acceptString = "Accept";
    private final String applicationJsonString = "application/json";
    private final String errorString = "Couldn't serialize response for content type application/json";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LinksApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<LinkResponse> linksDelete(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
        @Valid
        @RequestBody
        RemoveLinkRequest body
    ) {
        String accept = request.getHeader(acceptString);
        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                    "{\n  \"id\" : 1,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                    LinkResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListLinksResponse> linksGet(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    ) {
        String accept = request.getHeader(acceptString);

        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<ListLinksResponse>(objectMapper.readValue(
                    "{\n  \"size\" : 6,\n  \"links\" : [ {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  }, {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  } ]\n}",
                    ListLinksResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<ListLinksResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ListLinksResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LinkResponse> linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody
        AddLinkRequest body
    ) {
        String accept = request.getHeader(acceptString);

        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                    "{\n  \"id\" : 0,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                    LinkResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
