package com.mmarushika.demo_api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CreateUserRequest", description = "Payload accepted when registering a new user.")
public record CreateUserRequest(

		@Schema(
				description = "Full name of the user.",
				example = "Ada Lovelace",
				requiredMode = Schema.RequiredMode.REQUIRED,
				minLength = 1,
				maxLength = 100)
		@NotBlank
		@Size(min = 1, max = 100)
		String name,

		@Schema(
				description = "Unique email address of the user.",
				example = "ada@example.com",
				requiredMode = Schema.RequiredMode.REQUIRED,
				maxLength = 200)
		@NotBlank
		@Email
		@Size(max = 200)
		String email) {
}
