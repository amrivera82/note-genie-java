package spring.ai.example.spring_ai_demo.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.ai.example.spring_ai_demo.demo.service.NoteGenieService;
import spring.ai.example.spring_ai_demo.demo.type.NoteGenieRequest;
import spring.ai.example.spring_ai_demo.demo.type.NoteGenieResponse;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("/notegenie")
public class NoteGenieController {
    private final NoteGenieService noteGenieService;

    @GetMapping("amup")
    public String amUp() {
        return "Hey, there!";
    }
    
    @PostMapping("/beautify")
    public ResponseEntity<NoteGenieResponse> chat(@RequestBody NoteGenieRequest noteGenieRequest) {
        final String chatResponse =
            noteGenieService.call(noteGenieRequest.getPromptMessage(), noteGenieRequest.getHistoryId());
        return new ResponseEntity<>(new NoteGenieResponse(chatResponse), HttpStatus.OK);
    }

}
