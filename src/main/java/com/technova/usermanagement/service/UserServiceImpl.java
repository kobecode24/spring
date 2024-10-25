package com.technova.usermanagement.service;

import com.technova.usermanagement.model.User;
import com.technova.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Setter
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean identificationDocumentExists(String identificationDocument, Long excludeUserId) {
        Optional<User> existingUser = userRepository.findByIdentificationDocument(identificationDocument);
        return existingUser.isPresent() && !existingUser.get().getId().equals(excludeUserId);
    }
}
