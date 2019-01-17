package ch.hearc.jpa.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.jpa.entite.Personne;
import ch.hearc.jpa.entite.Sexe;

public interface PersonnePagingSortingRepository extends PagingAndSortingRepository<Personne, Long> {

	

	List<Personne> findBySexe(Sexe sexe);

}
