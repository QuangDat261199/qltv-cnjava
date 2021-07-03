package com.congnghejava.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@EnableJpaAuditing
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "slug")
	private String slug;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "highlight")
	private Boolean highlight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id") 
	private Category category;
	
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	@Column(name = "is_delete")
	private Boolean isDelete;

	public void setAll(Book book) {
		this.name = book.getName();
		this.slug = book.getSlug();
		this.author = book.getAuthor();
		this.image = book.getImage();
		this.price = book.getPrice();
		this.quantity = book.getQuantity();
		this.description = book.getDescription();
		this.content = book.getContent();
		this.isActive = book.getIsActive();
		this.highlight = book.getHighlight();
		this.category = book.getCategory();
		this.isDelete = book.getIsDelete();
	}

	public void updateQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getHighlight() {
		return highlight;
	}

	public void setHighlight(Boolean highlight) {
		this.highlight = highlight;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return "/uploads/book/" + this.image;
	}

	public String formatPrice() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(this.price);
	}
}
