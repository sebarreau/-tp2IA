package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.exception.RateLimitException;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {

        String apiKey = System.getenv("GEMINI_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Clé GEMINI_KEY manquante !");
        }

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

        poserQuestion(model, "Q1", "Explique en une phrase ce qu'est Internet.");

        Thread.sleep(5000);

        poserQuestion(model, "Q2", "Peux-tu me dire l'heure actuelle ?");

        Thread.sleep(5000);

        poserQuestion(model, "Q3", "Salut, je m'appelle Jean.");

        Thread.sleep(5000);

        poserQuestion(model, "Q4", "Comment je m'appelle ?");
    }

    private static void poserQuestion(ChatModel model, String numero, String question) {
        try {
            String reponse = model.chat(question);
            System.out.println(numero + " : " + question);
            System.out.println("R" + numero.substring(1) + " : " + reponse);
        } catch (RateLimitException e) {
            System.out.println(numero + " : " + question);
            System.out.println("Erreur : trop de requêtes. Attendez environ 1 minute puis relancez.");
        }
    }
}