package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.CreateMessageDTO;
import io.redspark.thot.controller.dto.OutputMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LeadChatController {

    @MessageMapping("chat")
    @SendTo("/leads/messages")
    public OutputMessageDTO send(CreateMessageDTO createMessageDTO){
        // TODO gravar no banco de dados
        return new OutputMessageDTO(
                createMessageDTO.getFrom(),
                createMessageDTO.getText(),
                LocalDateTime.now()
        );
    }

}
