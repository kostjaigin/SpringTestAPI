package com.example.pets_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pets_api.models.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
	Users findByUsername(String username);
}