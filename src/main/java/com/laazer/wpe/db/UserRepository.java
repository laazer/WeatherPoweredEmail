package com.laazer.wpe.db;

import com.laazer.wpe.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Laazer
 */
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findUserByTimeZone(final String timeZone);
}
