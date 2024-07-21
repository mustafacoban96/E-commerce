package com.shepherd.E_commerce.models;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@CreatedDate
	private Timestamp created_at;
	@LastModifiedDate
	private Timestamp update_at;

	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	
	@ManyToMany
	@JoinTable(
			name="cart_items",
			joinColumns = @JoinColumn(name="cart_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
	)
    private Set<Products> cartItems;
	
	
	
	
	
	
	
}
