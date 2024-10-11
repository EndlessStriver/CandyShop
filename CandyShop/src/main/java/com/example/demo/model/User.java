package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;
	
	@Column(name ="first_name", nullable = false)
	private String firstName;
	
	@Column(name ="last_name", nullable = false)
	private String lastName;
	
	@Column(name ="phone_number", nullable = true, unique = true)
	private String phoneNumber;
	
	@Column(name ="email", nullable = false, unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name ="gender", nullable = false)
	private Gender gender;
	
	@Column(name = "avatar", nullable = true)
	private String avatar;
	
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(name = "birthday", nullable = false)
	private LocalDate birthDay;
	
	@Column(name = "create_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> orders;
	
	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = UUID.randomUUID().toString();
        this.role = Role.CUSTOMER;
        
    }
	
	@PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
