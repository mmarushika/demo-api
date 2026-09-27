package com.mmarushika.demo_api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "User", description = "A user stored by the demo API.")
public record User(

		@Schema(
				description = "Server generated identifier.",
				example = "1",
				requiredMode = Schema.RequiredMode.REQUIRED,
				accessMode = Schema.AccessMode.READ_ONLY)
		Long id,

		@Schema(
				description = "Full name of the user.",
				example = "Ada Lovelace",
				requiredMode = Schema.RequiredMode.REQUIRED)
		String name,

		@Schema(
				description = "Unique email address of the user.",
				example = "ada@example.com",
				requiredMode = Schema.RequiredMode.REQUIRED)
		String email,

		@Schema(
				description = "Instant the user was created.",
				example = "2026-01-01T09:30:00Z",
				requiredMode = Schema.RequiredMode.REQUIRED,
				accessMode = Schema.AccessMode.READ_ONLY)
		Instant createdAt) {
}
