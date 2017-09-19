package com.test1;

import java.util.List;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

@RepositoryConfig(cacheName = "BreedCache")
public interface BreedRepository extends IgniteRepository<Breed, Long> {
	List<Breed> getAllBreedsByName(String name);

	@Query("SELECT id FROM Breed WHERE id = ?")
	List<Long> getById(long id, Pageable pageable);
}