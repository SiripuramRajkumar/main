package com.example.cardservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.cardservice.entity.Card;
import com.example.cardservice.repository.CardRepository;

@Service
public class CardFallBackService {
	
	 private final WebClient webClient;
	 public CardFallBackService(CardRepository repo, WebClient.Builder webClientBuilder) {
	        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
	    }
	 
	// api which will nested calling another api from 3rd party
	    @Transactional(readOnly = true)
	    public String getCardFallBackService(Card card,long cardId) {

	        String post = webClient.get()
	                .uri("/posts/1")
	                .retrieve()
	                .bodyToMono(String.class)
	                .block();

	        return String.format(
	                "{\"card\":{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\"},\"external\":%s}",
	                card.getId(), card.getName(), card.getEmail(), post
	        );
	    }

}
