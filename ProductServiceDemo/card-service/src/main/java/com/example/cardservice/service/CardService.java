package com.example.cardservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cardservice.dto.CardDTO;
import com.example.cardservice.entity.Card;
import com.example.cardservice.repository.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardFallBackService cardFallBackService;
   

    public CardService(CardRepository cardRepository, CardFallBackService cardFallBackService) {
        this.cardRepository = cardRepository;
        this.cardFallBackService = cardFallBackService;
    }

    @Transactional
    public CardDTO saveCard(Card user) {
        Card card = cardRepository.save(user);
        return new CardDTO(card.getId(), card.getName(), card.getEmail());
    }

    @Transactional
    public Optional<CardDTO> updateCard(Long id, Card update) {
        return cardRepository.findById(id).map(exist -> {
        	exist.setName(update.getName());
        	exist.setEmail(update.getEmail());
            Card card = cardRepository.save(exist);
            return new CardDTO(card.getId(), card.getName(), card.getEmail());
        });
    }

    @Transactional(readOnly = true)
    public Page<CardDTO> getCards(int page) {
        PageRequest pageCount = PageRequest.of(page, 10);
        return cardRepository.findAll(pageCount).map(card -> new CardDTO(card.getId(), card.getName(), card.getEmail()));
    }

    @Transactional(readOnly = true)
    public Optional<CardDTO> getCards(long id) {
        return cardRepository.findById(id).map(card -> new CardDTO(card.getId(), card.getName(), card.getEmail()));
    }
// api which will nested calling another api from 3rd party
    @Transactional(readOnly = true)
    public String getCardWithExternal(long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
       return cardFallBackService.getCardFallBackService(card,cardId);
       
    }
}
