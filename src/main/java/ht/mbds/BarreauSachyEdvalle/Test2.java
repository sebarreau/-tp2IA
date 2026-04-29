package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.TokenUsage;

public class Test2 {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Clé GEMINI_KEY manquante !");
        }

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash-lite")
                .temperature(0.7)
                .build();

        String question = "Explique en une phrase ce qu'est Internet.";

        ChatResponse response = model.chat(UserMessage.from(question));

        String reponse = response.aiMessage().text();

        TokenUsage tokenUsage = response.metadata().tokenUsage();

        int inputTokens = tokenUsage.inputTokenCount();
        int outputTokens = tokenUsage.outputTokenCount();
        int totalTokens = tokenUsage.totalTokenCount();

        System.out.println("Question : " + question);
        System.out.println("Réponse : " + reponse);

        System.out.println("\nTokens entrée : " + inputTokens);
        System.out.println("Tokens sortie : " + outputTokens);
        System.out.println("Total tokens : " + totalTokens);

        double prixInputParMillion = 0.30;
        double prixOutputParMillion = 2.50;

        double coutInput = (inputTokens / 1_000_000.0) * prixInputParMillion;
        double coutOutput = (outputTokens / 1_000_000.0) * prixOutputParMillion;
        double coutTotal = coutInput + coutOutput;

        System.out.println("\nCoût entrée : $" + coutInput);
        System.out.println("Coût sortie : $" + coutOutput);
        System.out.println("Coût total : $" + coutTotal);

        if (coutTotal > 0) {
            double nbRequetes = 1.0 / coutTotal;
            System.out.println("Nombre de requêtes pour 1$ : " + nbRequetes);
        } else {
            System.out.println("Coût nul, impossible de calculer.");
        }
    }
}