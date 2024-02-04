package com.amzmall.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String email;
	private String password;
	private String name;
	private String birth;
	private String phone;
	private String address;
	private String zipCode;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String status;
}
