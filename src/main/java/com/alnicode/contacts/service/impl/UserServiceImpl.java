package com.alnicode.contacts.service.impl;

import com.alnicode.contacts.dto.user.UserRequest;
import com.alnicode.contacts.dto.user.UserResponse;
import com.alnicode.contacts.mapper.UserMapper;
import com.alnicode.contacts.repository.IUserRepository;
import com.alnicode.contacts.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse create(UserRequest request) throws Exception {
        final var user = repository.findByUsernameOrEmailOrPhone(request.username(), request.email(), request.phone());

        if (user.isPresent()) {
            throw new Exception("This user already exists!");
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

}
