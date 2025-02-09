package com.sofiene.dropshop.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginAdmin {
	@NotEmpty(message="Email is required!")
	@Email(message="Please enter a valid email!")
	private String email;

	@NotEmpty(message="Password is required!")
	@Size(min=8, max=128, message="Password must be between 8 and 128 characters")
	private String password;

	public LoginAdmin() {}

	public String getEmail() {
		return email;
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
