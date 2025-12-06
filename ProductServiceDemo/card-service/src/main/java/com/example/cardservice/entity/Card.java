package com.example.cardservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Card_Id")
    private Long id;

    @Column(name = "holder_name", nullable = false)
    private String name;

    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    
    public Card() {
        // Set created_on automatically if DB doesn't
        this.createdDate = LocalDateTime.now();
    }

    public Card(String holderName, String emailId, String createdBy) {
        this.name = holderName;
        this.email = emailId;
        this.createdBy = createdBy;
        this.createdDate = LocalDateTime.now();
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

    
}

