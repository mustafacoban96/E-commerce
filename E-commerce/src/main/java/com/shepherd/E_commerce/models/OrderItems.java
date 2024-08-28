package com.shepherd.E_commerce.models;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
@Builder
@EqualsAndHashCode(of = {"id"})  // Only use the ID for equals and hashCode
public class OrderItems {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id",nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Orders order;
	
	private String name;
	private Integer quantity;
	private Long unit_price;
	private Long total_price;
	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Timestamp created_at;
	@UpdateTimestamp
	@Column(name="update_at")
	private Timestamp update_at;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Products products;
}
