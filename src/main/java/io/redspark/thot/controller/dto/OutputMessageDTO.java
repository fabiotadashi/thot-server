package io.redspark.thot.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputMessageDTO {

    private String from;
    private String text;
    private LocalDateTime date;

}
