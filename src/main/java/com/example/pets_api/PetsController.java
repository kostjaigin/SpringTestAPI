package com.example.pets_api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pets_api.models.Pets;
import com.example.pets_api.repositories.PetsRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
/**
 * The @RestController annotation tells Spring that 
 * this class will requested by URL 
 * and will return data to the requester
 * The @RequestMapping annotation specifies the base URL 
 * that the controller will be handling
 */
@RestController
public class PetsController {
	/**
	 * The @Autowired annotation creates an instance of the PetsRepository object 
	 * that will allow us to access and modify the pets database.
	 */
	@Autowired
	private PetsRepository repository;
	
	// REST ENDPOINTS: //
	
	/**
	 * return all pets on get request
	 * optionally also available @GetMapping("/") annotation
	 * @return
	 */
	@RequestMapping(value = "/pets", method = RequestMethod.GET)
	public List<Pets> getAllPets() {
		return repository.findAll();
	}
//	@GetMapping("/pets")
//	public List<Pets> getPetsWithParams(@RequestParam(value = "name", defaultValue = "") String name) {
//		if (name.equals("")) {
//			return repository.findAll();
//		} else {
//			return repository.findByname(name);
//		}
//	}
	
	/**
	 * return pet with id equal to the on provided in URL
	 * optionally also available @GetMapping("/{id}") annotation
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pets/{id}", method = RequestMethod.GET)
	public Pets getPetById(@PathVariable("id") ObjectId id) {
		return repository.findBy_id(id);
	}
	/**
	 * This mapping expects a request body (in JSON format) 
	 * with each of the fields that a Pets object contains (name, species, and breed). 
	 * The id in the request URL is the _id of the document to be modified.
	 * @param id
	 * @param pets
	 */
	@RequestMapping(value = "/pets/{id}", method = RequestMethod.PUT)
	public void modifyPetById(@PathVariable("id") ObjectId id, @Valid 
	@RequestBody Pets pets) {
	  pets.set_id(id);
	  repository.save(pets);
	}
	/**
	 * This mapping expects a request body (in JSON format) 
	 * with each of the fields that a Pets object contains (name, species, and breed), 
	 * and assigns it a new ObjectId. 
	 * This object is then inserted into the pets collection, 
	 * and the new Pets object is returned.
	 * @param pets
	 * @return
	 */
	@RequestMapping(value = "/pets", method = RequestMethod.POST)
	public Pets createPet(@Valid @RequestBody Pets pets) {
	  pets.set_id(ObjectId.get());
	  repository.save(pets);
	  return pets;
	}
	/**
	 * This endpoint takes the _id of a document in the pets collection
	 *  and removes that document from the collection.
	 * @param id
	 */
	@RequestMapping(value = "/pets/{id}", method = RequestMethod.DELETE)
	public void deletePet(@PathVariable ObjectId id) {
		repository.delete(repository.findBy_id(id));
	}
}
