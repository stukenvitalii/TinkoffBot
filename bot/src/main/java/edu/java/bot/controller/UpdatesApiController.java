package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.java.bot.model.Request.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {


    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UpdatesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> updatesPost(
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
