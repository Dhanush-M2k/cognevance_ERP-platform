package com.erp.platform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Column(name = "customer_email", length = 150)
    private String customerEmail;

    /** What the customer purchased (product/service being reviewed). */
    @Column(name = "purchase_item", nullable = false, length = 200)
    private String purchaseItem;

    /** 1-5 star rating. */
    @Column(nullable = false)
    private Integer rating;

    /** Free-text customer review. */
    @Column(name = "review_text", length = 2000)
    private String reviewText;

    @Column(length = 30)
    private String status; // NEW, REVIEWED, RESOLVED

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    public Feedback() {
        this.submittedAt = LocalDateTime.now();
        this.status = "NEW";
    }

    public Feedback(String customerName, String customerEmail, String purchaseItem,
                     Integer rating, String reviewText) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.purchaseItem = purchaseItem;
        this.rating = rating;
        this.reviewText = reviewText;
        this.status = "NEW";
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(String purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
