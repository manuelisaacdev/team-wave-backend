package com.teamwave.userservice.service;

import com.teamwave.userservice.dto.*;
import com.teamwave.userservice.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface UserService extends AbstractService<User, UUID> {
    User findByEmail(String email);
    List<User> findAll(Example<User> example, String orderBy, Sort.Direction direction);

    User create(UserDTO userDTO);
    User createArtist(UserArtistDTO userArtistDTO);
    User update(UUID userId, UpdateUserDTO updateUserDTO);

    void hasLoggedIn(User user);

    User recovery(RecoveryDTO recoveryDTO);
    User activate(UserEmailDTO userEmailDTO);
    User updatePassword(UUID userId, UpdateUserPasswordDTO updateUserPasswordDTO);
    User updateEmail(UUID userId, UpdateUserEmailDTO updateUserEmailDTO);

    EmailToken requireRecovery(UserEmailDTO userEmailDTO);
    EmailToken requireActivation(UserEmailDTO userEmailDTO);
    EmailToken requireUpdateEmail(UserEmailDTO userEmailDTO);
}
