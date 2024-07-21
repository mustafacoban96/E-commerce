package com.shepherd.E_commerce.models;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	
	@OneToMany(mappedBy = "cart",fetch = FetchType.LAZY)
    private Set<Products> cartItems;
	
	
	
	
	
	
	
}
