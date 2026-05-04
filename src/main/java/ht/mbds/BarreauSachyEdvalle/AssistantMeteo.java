package ht.mbds.BarreauSachyEdvalle;

import dev.langchain4j.service.SystemMessage;


public interface AssistantMeteo {
    String chat(String userMessage);
}