package com.shepherd.E_commerce.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@ToString(exclude = {"refreshToken", "cart", "orders"})
@EqualsAndHashCode(of = {"id"})  // Only use the ID for equals and hashCode
public class User implements UserDetails,Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	
	
	private boolean accountNonExpired;
	private boolean isEnabled;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Timestamp created_at;
	@UpdateTimestamp
	@Column(name="update_at")
	private Timestamp update_at;
	
	
	@ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
	@JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role",nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Roles> authorities;
	
	
	

	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RefreshToken refreshToken;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Orders> orders;
	
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, optional = false)
    private Cart cart;
	
	
	
}
