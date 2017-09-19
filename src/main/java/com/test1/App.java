package com.test1;

import java.sql.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.test1" })
public class App {

	private static AnnotationConfigApplicationContext ctx;
	private static BreedRepository breedRepository;
	private static DogRepository dogRepository;

	public static void main(String[] args) {
		System.out.println("Spring Data with ignite Example!");
		ctx = new AnnotationConfigApplicationContext();

		ctx.register(App.class);
		// ctx.register(MongoConfig.class);
		ctx.refresh();

		String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for (String string : beanDefinitionNames) {
			System.out.println("***********" + string + "***********");
		}

		breedRepository = ctx.getBean(BreedRepository.class);
		dogRepository = ctx.getBean(DogRepository.class);

		// fill the repository with data and Save
		Breed collie = new Breed();
		collie.setId(1L);
		collie.setName("collie");
		// save Breed with name collie
		breedRepository.save(1L, collie);
		System.out.println("Add one breed in the repository!");
		// Query the breed
		List<Breed> getAllBreeds = breedRepository.getAllBreedsByName("collie");
		for (Breed breed : getAllBreeds) {
			System.out.println("Breed:" + breed);
		}

		// Add some dogs
		Dog dina = new Dog();
		dina.setName("dina");
		dina.setId(1L);
		dina.setBreedid(1L);
		dina.setBirthdate(new Date(System.currentTimeMillis()));
		// Save Dina
		dogRepository.save(2L, dina);

		System.out.println("Dog dina save into the cache! @" + new Date(System.currentTimeMillis()));
		// Query the Dog Dina
		List<Dog> dogs = dogRepository.getDogByName("dina");
		for (Dog dog : dogs) {
			System.out.println("Dog:" + dog);
		}
		ctx.destroy();
	}
}