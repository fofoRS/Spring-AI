package org.demo.ai.entityResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai/books")
public class BooksController {

    private final ChatClient chatClient;


    public BooksController(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("book/recommend")
    public BookRecommendation recommend(@RequestParam(value = "topic", defaultValue = "finance") String topic) {
        String userMessage =
                """
                Give the top ranked book name about {topic},
                the book's author and a list of 3 suggestions about this book,
                include what this book is best for in the suggestions.
                """;
        PromptTemplate userPromptTemplate = new PromptTemplate(userMessage);
        Prompt userPrompt = userPromptTemplate.create(Map.of("topic", topic));
        return  chatClient
                .prompt()
                .user(userPrompt.getContents())
                .call()
                .entity(BookRecommendation.class);
    }
}
