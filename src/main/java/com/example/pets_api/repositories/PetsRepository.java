package com.example.pets_api.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pets_api.models.Pets;

/**
 * Connector between the model and MongoDB.
 * Name of this repository is extremely important
 * because it tells MongoDB the collection that it will be querying.
 * Alternatively we can use @RepositoryRestResource(collectionResourceRel = <COLLECTIONNAME>, path = <PATH>)
 * to show what to query.
 * This interface will extend the MongoRepository class, 
 * which already contains generic methods
 *  like save (for creating/updating documents) 
 *  and delete (for removing documents).
 */
public interface PetsRepository extends MongoRepository<Pets, String> {
	/**
	 * we do not need to manually implement these queries, 
	 * we can simply use Spring Boot’s repository naming conventions, 
	 * and the MongoRepository will intelligently construct the queries at runtime
	 */
	Pets findBy_id(ObjectId _id);
	List<Pets> findByName(String name); // does not work as expected...
}
