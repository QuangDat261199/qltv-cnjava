package com.congnghejava.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.util.Date;

@Entity
@EnableJpaAuditing
@Table(name = "slides")
public class Slide {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "content")
	private String content;

	@Column(name = "image")
	private String image;
	
	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "link")
	private String link;

	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public void setAll(Slide slide) {
		this.name = slide.getName();
		this.description = slide.getDescription();
		this.content = slide.getContent();
		this.image = slide.getImage();
		this.isActive = slide.getIsActive();
		this.link = slide.getLink();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getImageUrl() {
		return "/uploads/slide/" + this.image;
	}
}
