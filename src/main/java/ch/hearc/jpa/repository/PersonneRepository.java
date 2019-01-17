package ch.hearc.jpa.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.jpa.entite.Personne;
import ch.hearc.jpa.entite.Sexe;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

	List<Personne> findBySexe(Sexe sexe);

}
