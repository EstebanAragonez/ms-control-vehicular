package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.domain.model.User;
import com.apivehicular.domain.repository.UserRepository;
import com.apivehicular.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final R2dbcUserRepository r2dbcUserRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<User> findById(Long id) {
        return r2dbcUserRepository.findById(id)
                .flatMap(userEntity -> userMapper.toModel(userEntity));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return r2dbcUserRepository.findByEmail(email)
                .flatMap(userEntity -> userMapper.toModel(userEntity));
    }

    @Override
    public Mono<User> findByCedula(String cedula) {
        return r2dbcUserRepository.findByCedula(cedula)
                .flatMap(userEntity -> userMapper.toModel(userEntity));
    }

    @Override
    public Flux<User> findAll() {
        return r2dbcUserRepository.findAll()
                .flatMap(userEntity -> userMapper.toModel(userEntity));
    }

    @Override
    public Mono<User> save(User user) {
        return userMapper.toEntity(user)
                .flatMap(userEntity -> r2dbcUserRepository.save(userEntity))
                .flatMap(savedEntity -> userMapper.toModel(savedEntity));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcUserRepository.deleteById(id);
    }

}
