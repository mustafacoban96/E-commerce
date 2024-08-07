package com.shepherd.E_commerce.models;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Timestamp created_at;
	@UpdateTimestamp
	@Column(name="update_at")
	private Timestamp update_at;

	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	@ToString.Exclude
	private User user;
	
	
	@ManyToMany
	@JoinTable(
			name="cart_items",
			joinColumns = @JoinColumn(name="cart_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
	)
    private Set<Products> cartItems;
	
	
	
	
	
	
	
}
