package com.example.cardservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardservice.dto.CardDTO;
import com.example.cardservice.entity.Card;
import com.example.cardservice.service.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Card", description = "Card management APIs")
@RestController
@RequestMapping("/api/v1")
public class CardController {
	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@Operation(summary = "Create Card", description = "Create a new Card")
	@PostMapping("/cards/create")
	public ResponseEntity<CardDTO> saveCard(@RequestBody Card card) {
		CardDTO created = cardService.saveCard(card);
		return ResponseEntity.ok(created);
	}

	@Operation(summary = "update card", description = "update existing card")
	@PutMapping("/cards/update/{id}")
	public ResponseEntity<CardDTO> updateCard(@PathVariable Long id, @RequestBody Card card) {
		return cardService.updateCard(id, card).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

//api with Pagination (Each Page 10 records)
	@Operation(summary = "List Cards", description = "Get paginated list of cards (page query param)")
	@GetMapping("/cards")
	public ResponseEntity<Page<CardDTO>> getAllCardsDetail(@RequestParam(defaultValue = "0") int page) {
		Page<CardDTO> result = cardService.getCards(page);
		return ResponseEntity.ok(result);
	}

	// @Operation(summary = "GET Card", description = "Get card by id")
	@GetMapping("/cards/{id}")
	public ResponseEntity<CardDTO> getCardDetail(@PathVariable Long id) {
		return cardService.getCards(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// api which will nested calling another api from 3rd party
	@GetMapping("/cards/{id}/external")
	public ResponseEntity<String> getCardWithExternal(@PathVariable Long cardId) {
		try {
			String combined = cardService.getCardWithExternal(cardId);
			return ResponseEntity.ok(combined);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("{error:" + ex.getMessage() + "}");
		}
	}

}
