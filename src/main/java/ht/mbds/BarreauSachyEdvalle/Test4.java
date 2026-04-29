package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel.TaskType;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test4 {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Clé GEMINI_KEY manquante !");
        }

        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-embedding-001")
                .taskType(TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofSeconds(10))
                .build();

        comparer(embeddingModel,
                "Le chat dort sur le canapé.",
                "Un félin se repose sur le sofa.");

        comparer(embeddingModel,
                "J'aime apprendre Java.",
                "Je suis en train d'étudier la programmation Java.");

        comparer(embeddingModel,
                "Il pleut beaucoup aujourd'hui.",
                "La voiture rouge est garée devant la maison.");

        comparer(embeddingModel,
                "Je vais acheter du pain à la boulangerie.",
                "Je pars chercher une baguette chez le boulanger.");
    }

    private static void comparer(EmbeddingModel model, String phrase1, String phrase2) {

        Response<Embedding> response1 = model.embed(phrase1);
        Response<Embedding> response2 = model.embed(phrase2);

        Embedding embedding1 = response1.content();
        Embedding embedding2 = response2.content();

        double similarite = CosineSimilarity.between(embedding1, embedding2);

        System.out.println("Phrase 1 : " + phrase1);
        System.out.println("Phrase 2 : " + phrase2);
        System.out.println("Similarité cosinus : " + similarite);
        System.out.println("-----------------------------------");
    }
}