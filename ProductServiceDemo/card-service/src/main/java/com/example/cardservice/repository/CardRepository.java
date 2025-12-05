package com.example.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cardservice.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
