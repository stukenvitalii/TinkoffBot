package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.model.dto.Chat;
import edu.java.service.ChatService;
import edu.java.service.jdbc.JdbcChatService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatApiController implements TgChatApi {
    @Autowired
    private final ChatService chatService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TgChatApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final String acceptString = "Accept";

    @Autowired
    public TgChatApiController(ChatService chatService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.chatService = chatService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> tgChatIdDelete(
        @Parameter(in = ParameterIn.PATH,
                   description = "",
                   required = true,
                   schema = @Schema())
        @PathVariable("id") Long id
    ) {
        chatService.removeChat(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> tgChatIdPost(
        @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id")
        Long id
    ) {
        Chat chat = new Chat();
        chat.setChatId(id);

        chatService.addChat(chat);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
