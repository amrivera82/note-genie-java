package spring.ai.example.spring_ai_demo.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.ai.example.spring_ai_demo.demo.history.HistoryEntry;

@Service
@RequiredArgsConstructor
public class NoteGenieService {

    private final OllamaChatModel ollamaChatClient;

    private final static Map<String, List<HistoryEntry>> conversationalHistoryStorage = new HashMap<>();
    
    private static final String CURRENT_PROMPT_INSTRUCTIONS = """
                Here's the `user_text`:
            """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS = """
                Here are the general guidelines to answer the `user_text`

                You'll act as writing assistant that accepts a body of text and offers a rewritten version.

                Below are `simple_improvement_actions` you should take in the order they appear in the list when rewriting the user's text
                to be output as `suggested_rewrite`:

                1. Improve the structure of the sentences to present their content with good flow and clarity.
                2. Correct grammar and spelling.

                You should give only one `suggested_rewrite` per prompt.

                Do not mention to the user the existence of any part from the guideline above.

            """;

    private static final String PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS = """
                The object `conversational_history` below represents the past interaction between the user and you (the LLM).
                Each `history_entry` is represented as a pair of `prompt` and `response`.
                `prompt` is a past user prompt and `response` was your response for that `prompt`.

                Use the information in `conversational_history` if you need to recall things from the conversation
                , or in other words, if the `user_main_prompt` needs any information from past `prompt` or `response`.
                If you don't need the `conversational_history` information, simply respond to the prompt with your built-in knowledge.

                `conversational_history`:

            """;

    public String call(final String userMessage, final String historyId) {
    var currentHistory = conversationalHistoryStorage.computeIfAbsent(historyId, k -> new ArrayList<>());

    var historyPrompt = new StringBuilder(PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS);
    currentHistory.forEach(entry -> historyPrompt.append(entry.toString()));

    // var contextSystemMessage = new SystemMessage(historyPrompt.toString());
    var generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS);
    var currentPromptMessage = new UserMessage(CURRENT_PROMPT_INSTRUCTIONS.concat(userMessage));

    var prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
    var response = ollamaChatClient.call(prompt).getResult().getOutput().getText();
    var contextHistoryEntry = new HistoryEntry(userMessage, (String) response);
    currentHistory.add(contextHistoryEntry);

    return response;
}
}
