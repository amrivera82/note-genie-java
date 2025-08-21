package spring.ai.example.spring_ai_demo.demo.history;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HistoryEntry {

    private String prompt;
    private String response;

    @Override
    public String toString() {
        return String.format("""
                        `history_entry`:
                            `prompt`: %s
                        
                            `response`: %s
                        -----------------
                       \n
            """, prompt, response);
    }
}
