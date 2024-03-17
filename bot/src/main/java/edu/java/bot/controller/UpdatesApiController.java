package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.model.request.LinkUpdateRequest;
import edu.java.bot.service.MessageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {
    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final MessageService messageService;

    @Autowired
    public UpdatesApiController(ObjectMapper objectMapper, HttpServletRequest request, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.messageService = messageService;
    }

    public ResponseEntity<Void> updatesPost(
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        messageService.sendNotification(body.getTgChatIds(), body.getUrl(), body.getDescription());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
