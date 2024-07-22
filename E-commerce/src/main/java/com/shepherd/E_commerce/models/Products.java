package com.shepherd.E_commerce.models;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//https://medium.com/@husnapoyraz88/hibernate-entity-ler-aras%C4%B1nda-i%CC%87li%C5%9Fki-tan%C4%B1mlamada-kullan%C4%B1lan-temel-anatasyonlar-8b8fd0396aef

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Builder
public class Products {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String description;
	private Integer stock;
	private Long price;
	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Timestamp created_at;
	@UpdateTimestamp
	@Column(name="update_at")
	private Timestamp update_at;

	
	
}
