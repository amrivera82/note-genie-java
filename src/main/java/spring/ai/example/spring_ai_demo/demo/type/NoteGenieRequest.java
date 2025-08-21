package spring.ai.example.spring_ai_demo.demo.type;

import lombok.Data;

@Data
public class NoteGenieRequest {
    private String promptMessage;
    private String historyId;
}
