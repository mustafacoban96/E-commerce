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
@Table(name = "orders")
@Builder
@EqualsAndHashCode(of = {"id"})  // Only use the ID for equals and hashCode
public class Orders {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private Long total_price;

	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Timestamp created_at;
	@UpdateTimestamp
	@Column(name="update_at")
	private Timestamp update_at;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	// when parent is deleted, child are going to be deleted
	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore is used to ignore the logical property used in serialization and deserialization.
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "order",cascade = {CascadeType.REMOVE})
	private Set<OrderItems> orderItems;
}
