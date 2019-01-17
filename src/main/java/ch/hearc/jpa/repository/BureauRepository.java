package ch.hearc.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.jpa.entite.Adresse;
import ch.hearc.jpa.entite.Bureau;

public interface BureauRepository extends CrudRepository<Bureau, Long> {

}
