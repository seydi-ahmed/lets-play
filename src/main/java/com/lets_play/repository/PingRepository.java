package com.lets_play.repository;

import com.lets_play.model.Ping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PingRepository extends MongoRepository<Ping, String> {
}
