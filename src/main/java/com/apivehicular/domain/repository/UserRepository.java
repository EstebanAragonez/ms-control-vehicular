package com.apivehicular.domain.repository;


import com.apivehicular.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findById(Long id);
    Mono<User> findByEmail(String email);
    Mono<User> findByCedula(String cedula);
    Flux<User> findAll();
    Mono<User> save(User user);
    Mono<Void> deleteById(Long id);
}