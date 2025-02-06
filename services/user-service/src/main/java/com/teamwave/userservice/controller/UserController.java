package com.teamwave.userservice.controller;

import com.teamwave.userservice.dto.*;
import com.teamwave.userservice.model.Gender;
import com.teamwave.userservice.model.User;
import com.teamwave.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Gender gender,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) UUID countryId,
        @RequestParam(required = false) Boolean locked,
        @RequestParam(required = false) Boolean enabled,
        @RequestParam(defaultValue = "name") String orderBy,
        @RequestParam(defaultValue = "ASC") Direction direction
    ){
        return ResponseEntity.ok(userService.findAll(
            Example.of(User.builder()
            .name(name)
            .gender(gender)
            .email(email)
            .locked(locked)
            .enabled(enabled)
            .countryId(countryId)
            .build(),
            ExampleMatcher.matching()
        .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
        .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
        .withIgnoreCase()
        ), orderBy, direction));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<User>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UUID countryId,
            @RequestParam(required = false) Boolean locked,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Direction direction
    ){
        return ResponseEntity.ok(userService.findAll(
        page, size,
        Example.of(User.builder()
            .name(name)
            .gender(gender)
            .email(email)
            .locked(locked)
            .enabled(enabled)
            .countryId(countryId)
            .build(),
        ExampleMatcher.matching()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
            .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
            .withIgnoreCase()
        ), orderBy, direction));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PostMapping("/artist")
    public ResponseEntity<User> create(@RequestBody @Valid UserArtistDTO userArtistDTO){
        return ResponseEntity.ok(userService.createArtist(userArtistDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable UUID userId, @RequestBody @Valid UpdateUserDTO updateUserDTO){
        return ResponseEntity.ok(userService.update(userId, updateUserDTO));
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<User> updatePassword(@PathVariable UUID userId, @RequestBody @Valid UpdateUserPasswordDTO updateUserPasswordDTO){
        return ResponseEntity.ok(userService.updatePassword(userId, updateUserPasswordDTO));
    }

    // RECOVERY
    @PatchMapping("/recovery")
    public ResponseEntity<User> recovery(@RequestBody @Valid RecoveryDTO recoveryDTO) {
        return ResponseEntity.ok(userService.recovery(recoveryDTO));
    }

    @PostMapping("/recovery")
    public ResponseEntity<EmailToken> requireRecovery(@RequestBody @Valid UserEmailDTO userEmailDTO) {
        return ResponseEntity.ok(userService.requireRecovery(userEmailDTO));
    }

    // ACTIVATION ACCOUNT
    @PatchMapping("/activation")
    public ResponseEntity<User> activate(@RequestBody @Valid UserEmailDTO userEmailDTO){
        return ResponseEntity.ok(userService.activate(userEmailDTO));
    }

    @PostMapping("/activation")
    public ResponseEntity<EmailToken> requireActivation(@RequestBody @Valid UserEmailDTO userEmailDTO){
        return ResponseEntity.ok(userService.requireActivation(userEmailDTO));
    }

    // UPDATE EMAIL
    @PostMapping("/email")
    public ResponseEntity<EmailToken> requiredUpdateEmail(@RequestBody @Valid UserEmailDTO userEmailDTO){
        return ResponseEntity.ok(userService.requireUpdateEmail(userEmailDTO));
    }

    @PatchMapping("/email/{userId}")
    public ResponseEntity<User> updateEmail(@PathVariable UUID userId, @RequestBody @Valid UpdateUserEmailDTO updateUserEmailDTO){
        return ResponseEntity.ok(userService.updateEmail(userId, updateUserEmailDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
