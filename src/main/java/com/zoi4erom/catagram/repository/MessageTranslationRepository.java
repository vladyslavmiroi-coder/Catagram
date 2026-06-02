package com.zoi4erom.catagram.repository;

import com.zoi4erom.catagram.entity.MessageTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageTranslationRepository extends JpaRepository<MessageTranslation,Long> {

        Optional<MessageTranslation> findByMessageIdAndLanguageCode(Long messageId, String languageCode);
}
