package com.congnghejava.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@EnableJpaAuditing
@Table(name = "returns")
public class Return {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "indemnity")
	private Long indemnity; // tien boi thuong
	
	@Column(name = "fine")
	private Long fine; // tien tre 5k/ngay
	
	@Column(name = "total")
	private Long total; // tong tien
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	private User user;

	@Column(name = "deadline")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "borrow_id")
	private Borrow borrow;

	@OneToMany(mappedBy = "returnBorrow")
	private List<ReturnDetail> details;
	
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public Long getIndemnity() {
		return indemnity;
	}

	public void setIndemnity(Long indemnity) {
		this.indemnity = indemnity;
	}

	public Long getFine() {
		return fine;
	}

	public void setFine(Long fine) {
		this.fine = fine;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReturnDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ReturnDetail> details) {
		this.details = details;
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public String formatMoney(Long money) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(money);
	}
}
