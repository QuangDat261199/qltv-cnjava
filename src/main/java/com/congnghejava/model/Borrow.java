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
@Table(name = "borrows")
public class Borrow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "deadline")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;
	
	@Column(name = "total")
	private Long total;
	
	@Column(name = "payment")
	private Long payment;
	
	@Column(name = "status")
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "borrow")
	private List<BorrowDetail> details;
	
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public void setAll(Borrow borrow) {
		this.deadline = borrow.getDeadline();
		this.total = borrow.getTotal();
		this.payment = borrow.getPayment();
		this.status = borrow.getStatus();
		this.user = borrow.getUser();
	}

	public List<BorrowDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BorrowDetail> details) {
		this.details = details;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPayment() {
		return payment;
	}

	public void setPayment(Long payment) {
		this.payment = payment;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String formatMoney(Long money) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(money);
	}
}
