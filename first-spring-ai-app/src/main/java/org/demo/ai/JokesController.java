package org.demo.ai;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai/jokes")
public class JokesController {
    private final OpenAiChatModel chatModel;

    public JokesController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ask-for-joke")
    public String getJoke(@RequestParam(value = "topic", defaultValue = "computers") String topic) {
        String promptMessage = "Please give a joke about {topic}";
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        return chatModel.call(prompt).getResult().getOutput().getContent();
    }

    @GetMapping("/ask-for-joke-with-actor-tone")
    public String getJoke(@RequestParam(value = "topic", defaultValue = "computers") String topic,
                          @RequestParam(value = "tone-of-actor", defaultValue = "Clint EastWood") String tone) {
        String sysMessageString =
          "Please response to the request of joke with the tone of {tone}, " +
                        "at final of  the joke gives a {tone} famous phrase";

        String userMessageString = "Give a joke about {topic}";

        SystemPromptTemplate systemTemplate = new SystemPromptTemplate(sysMessageString);
        Message systemMessage = systemTemplate.createMessage(Map.of("tone",tone));


        PromptTemplate userPromptTemplate = new PromptTemplate(userMessageString);
        Message userMessage = userPromptTemplate.createMessage(Map.of("topic",topic));

        return chatModel.call(systemMessage,userMessage);
    }
}
