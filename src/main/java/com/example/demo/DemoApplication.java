package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.Transactional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(PersonService personService) {

		return args -> {
			Person person = personService.create("Larry");
			person.setName("Lily");
		};
	}
}

@Service
class PersonService {
	private final PersonRepository personRepository;

	PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Transactional
	public Person create(String name) {
		Person p = new Person(name);
		Person person = personRepository.save(p);
		person.setName("Lana");
		return person;
	}
}

interface PersonRepository extends CrudRepository<Person, String> {}

@Entity
class Person {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Person(String name) {
		this.name = name;
	}

	public Person() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}