package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.MessageDTO;
import io.redspark.thot.controller.dto.OutputMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping
public class LeadChatController {

    @MessageMapping("chat")
    @SendTo("/leads/messages")
    public OutputMessageDTO send(MessageDTO messageDTO) {
        return new OutputMessageDTO(messageDTO.getFrom(),
                messageDTO.getText(),
                LocalDateTime.now());
    }

}
