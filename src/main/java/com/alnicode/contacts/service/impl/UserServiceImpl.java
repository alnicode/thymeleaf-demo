package com.alnicode.contacts.service.impl;

import com.alnicode.contacts.dto.UserRequest;
import com.alnicode.contacts.dto.UserResponse;
import com.alnicode.contacts.mapper.IUserMapper;
import com.alnicode.contacts.repository.IUserRepository;
import com.alnicode.contacts.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;
    private final IUserMapper mapper;

    @Override
    @Transactional
    public UserResponse create(UserRequest request) throws Exception {
        final var user = repository.findByUsernameOrEmailOrPhone(request.username(), request.email(), request.phone());

        if (user.isPresent()) {
            throw new Exception("This user already exists!");
        }

        if (!request.passwordsMatch()) {
            throw new Exception("Please, confirm your password.");
        }

        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> get(long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Optional<UserResponse> update(long id, UserRequest request) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }

        final var entity = mapper.toEntity(request);
        entity.setId(id);

        return Optional.of(mapper.toResponse(repository.save(entity)));
    }

    @Override
    public boolean delete(long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public void addContact(long userId, long contactId) throws Exception {
        final var user = repository.findById(userId)
                .orElseThrow(() -> new Exception("The user with ID #" + userId + " not exists!"));

        final var contact = repository.findById(contactId)
                .orElseThrow(() -> new Exception("The contact with ID #" + contactId + " not exists!"));

        if (userId == contactId) {
            throw new Exception("You can't add yourself as a contact.");
        }

        user.addContact(contact);

        repository.save(user);
    }

    @Override
    @Transactional
    public void removeContact(long userId, long contactId) throws Exception {
        final var user = repository.findById(userId)
                .orElseThrow(() -> new Exception("The user with ID #" + userId + " not exists!"));

        final var contact = repository.findById(contactId)
                .orElseThrow(() -> new Exception("The contact with ID #" + contactId + " not exists!"));

        final var isAdded = user.getContacts()
                .stream()
                .anyMatch(_contact -> _contact.getId() == contactId);

        if (!isAdded) {
            throw new Exception("You don't have added a contact with ID #" + contactId);
        }

        user.removeContact(contact);

        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<UserResponse>> getContacts(long userId) {
        final var user = repository.findById(userId);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mapper.toResponses(new ArrayList<>(user.get().getContacts())));
    }
}
