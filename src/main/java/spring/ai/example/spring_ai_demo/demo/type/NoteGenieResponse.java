package spring.ai.example.spring_ai_demo.demo.type;

import lombok.Data;

@Data
public class NoteGenieResponse {
    private String result;

    public NoteGenieResponse(String result) {
        this.result = result;
    }
}
