package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import ht.mbds.BarreauSachyEdvalle.outil.meteo.MeteoTool;

import java.util.Scanner;

public class Test7 {

    public static void main(String[] args) {

        String llmKey = System.getenv("GEMINI_KEY");



        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.3)
                .logRequestsAndResponses(true)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                        .tools(new MeteoTool())
                        .build();

        conversation(assistant);
    }

    private static void conversation(AssistantMeteo assistant) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Assistant météo pour vous servir !");
        System.out.println("Tapez 'fin' pour quitter.\n");

        while (true) {

            System.out.print("Posez votre question : ");
            String question = scanner.nextLine();

            if (question == null || question.isBlank()) continue;

            if ("fin".equalsIgnoreCase(question)) {
                System.out.println(" Fin de la conversation.");
                break;
            }

            try {
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        scanner.close();
    }
}