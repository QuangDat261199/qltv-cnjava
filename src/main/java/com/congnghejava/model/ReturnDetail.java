package com.congnghejava.model;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Entity
@EnableJpaAuditing
@Table(name = "return_details")
public class ReturnDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "quantity_borrow")
	private Integer quantityBorrow;

	@Column(name = "indemnity")
	private Long indemnity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "return_id") 
	private Return returnBorrow;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id") 
	private com.congnghejava.model.Book book;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getIndemnity() {
		return indemnity;
	}

	public void setIndemnity(Long indemnity) {
		this.indemnity = indemnity;
	}

	public Return getReturnBorrow() {
		return returnBorrow;
	}

	public void setReturnBorrow(Return returnBorrow) {
		this.returnBorrow = returnBorrow;
	}

	public com.congnghejava.model.Book getBook() {
		return book;
	}

	public void setBook(com.congnghejava.model.Book book) {
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public Integer getQuantityBorrow() {
		return quantityBorrow;
	}

	public void setQuantityBorrow(Integer quantityBorrow) {
		this.quantityBorrow = quantityBorrow;
	}
}
