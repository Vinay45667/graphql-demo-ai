package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AiController {

    private final ChatClient chatClient;

    // ChatClient.Builder is auto-configured by Spring AI once a chat model
    // starter (here: OpenAI) is on the classpath. We build one ChatClient from it.
    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // GraphQL: query { recommendBooks(topic: "space exploration") }
    @QueryMapping
    public String recommendBooks(@Argument String topic) {
        return chatClient.prompt()
                .user("Recommend three real books about " + topic
                        + ". Reply with just the title and author of each, one per line.")
                .call()
                .content();
    }
}
