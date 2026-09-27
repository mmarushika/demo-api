package com.mmarushika.demo_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory user directory. This exists only to produce an OpenAPI contract for
 * the compatibility gate, so there is no persistence and no business logic.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "In-memory user directory exposed by the demo API.")
public class UserController {

	private final Map<Long, User> users = new ConcurrentHashMap<>();

	private final AtomicLong sequence = new AtomicLong();

	public UserController() {
		create(new CreateUserRequest("Ada Lovelace", "ada@example.com"));
		create(new CreateUserRequest("Grace Hopper", "grace@example.com"));
	}

	@GetMapping
	@Operation(summary = "List users", description = "Returns every user currently held in memory.")
	@ApiResponse(responseCode = "200", description = "Users ordered by identifier.")
	public List<User> listUsers() {
		return this.users.values().stream()
				.sorted(Comparator.comparing(User::id))
				.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a user by id")
	@ApiResponse(responseCode = "200", description = "The requested user.")
	@ApiResponse(responseCode = "404", description = "No user exists for the supplied identifier.")
	public ResponseEntity<User> getUser(
			@Parameter(description = "Identifier of the user.", example = "1", required = true)
			@PathVariable Long id) {

		User user = this.users.get(id);

		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping
	@Operation(summary = "Create a user", description = "Stores a new user in memory and returns it.")
	@ApiResponse(responseCode = "201", description = "The user that was created.")
	public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
		User user = create(request);

		return ResponseEntity.created(URI.create("/users/" + user.id())).body(user);
	}

	private User create(CreateUserRequest request) {
		long id = this.sequence.incrementAndGet();
		User user = new User(id, request.name(), request.email(), Instant.now());
		this.users.put(id, user);

		return user;
	}

}
