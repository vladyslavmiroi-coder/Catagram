package com.zoi4erom.catagram.service;

import com.deepl.api.*;
import com.zoi4erom.catagram.entity.MessageTranslation;
import com.zoi4erom.catagram.repository.MessageRepository;
import com.zoi4erom.catagram.repository.MessageTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TranslationService {

        @Value("${deepl.api.key}")
        private String authKey;

        private final MessageTranslationRepository translationRepository;
        private final MessageRepository messageRepository;
        private Translator translator;

        @PostConstruct
        public void init() {
                this.translator = new Translator(authKey);
        }

        public String getOrTranslate(Long messageId, String targetLanguageCode) {
                return translationRepository.findByMessageIdAndLanguageCode(messageId, targetLanguageCode)
                        .map(MessageTranslation::getContent)
                        .orElseGet(() -> {
                                var messageEntity = messageRepository.findById(messageId)
                                        .orElseThrow(() -> new RuntimeException("Message not found"));

                                String translatedText = translate(messageEntity.getContent(), targetLanguageCode);

                                MessageTranslation translation = MessageTranslation.builder()
                                        .message(messageEntity)
                                        .languageCode(targetLanguageCode)
                                        .content(translatedText)
                                        .build();
                                translationRepository.save(translation);

                                return translatedText;
                        });
        }

        private String translate(String text, String targetLanguageCode) {
                try {
                        String targetTag = targetLanguageCode.toUpperCase();
                        if (targetTag.equals("EN")) targetTag = "EN-US";
                        if (targetTag.equals("PT")) targetTag = "PT-PT";

                        TextResult result = translator.translateText(text, null, targetTag);
                        return result.getText();
                } catch (DeepLException | InterruptedException e) {
                        return text;
                }
        }
}