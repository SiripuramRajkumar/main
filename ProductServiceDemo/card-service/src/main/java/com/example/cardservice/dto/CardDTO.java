package com.example.cardservice.dto;

public class CardDTO {
	private Long id;
	private String name;
	private String email;
	private String createdBy;

	public CardDTO() {
	}

	public CardDTO(Long id, String name, String email,String createdBy) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.createdBy=createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
