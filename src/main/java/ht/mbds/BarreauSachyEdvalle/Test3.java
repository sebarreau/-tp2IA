package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class Test3 {

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

        PromptTemplate template = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}"
        );

        Map<String, Object> variables = Map.of(
                "texte", "Bonjour, comment vas-tu aujourd'hui ?"
        );

        Prompt prompt = template.apply(variables);

        String response = model.chat(prompt.text());

        System.out.println("Texte original : " + variables.get("texte"));
        System.out.println("Traduction : " + response);
    }
}