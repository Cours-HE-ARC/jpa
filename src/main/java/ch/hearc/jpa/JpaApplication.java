package ch.hearc.jpa;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import ch.hearc.jpa.entite.Adresse;
import ch.hearc.jpa.entite.Bureau;
import ch.hearc.jpa.entite.Employee;
import ch.hearc.jpa.entite.Personne;
import ch.hearc.jpa.entite.Sexe;
import ch.hearc.jpa.repository.AdresseRepository;
import ch.hearc.jpa.repository.BureauRepository;
import ch.hearc.jpa.repository.EmployeeRepository;
import ch.hearc.jpa.repository.PersonnePagingSortingRepository;
import ch.hearc.jpa.repository.PersonneRepository;

@SpringBootApplication
public class JpaApplication {

	@Autowired
	PersonneRepository prepo;
	
	@Autowired
	PersonnePagingSortingRepository pgrepo;
	
	@Autowired
	AdresseRepository arepo;
	
	@Autowired
	EmployeeRepository emrepo;
	
	@Autowired
	BureauRepository brepo;
	
	@Autowired
	Faker faker;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
	
	
	@Bean
	public Faker faker () {
		return new Faker();
	}
	
	@PostConstruct
	public void init() {
		
		IntStream.range(0, 100).forEach(action->{
			
			Personne p = generatePersonne();
			
			prepo.save(p);
			
			Adresse a = generateAdresse();
			
			p.setAdresse(arepo.save(a));
			
			prepo.save(p);
			
		});
		
		System.out.println("*********** All personns crud repo ***********");
		
		prepo.findAll().forEach(personne -> {
			System.out.println(personne);
		});
		
		System.out.println("*********** All personns page, 1page, 12 results repo ***********");
		
		pgrepo.findAll(PageRequest.of(0,12, Sort.by("nom"))).forEach(personne -> {
			System.out.println(personne);
		});
		
		System.out.println("*********** Feminine personns ***********");
		
		prepo.findBySexe(Sexe.FEMININ).forEach(personne-> {
			System.out.println(personne);
		});
		
		System.out.println("*********** First 25 Feminine personns ***********");
		
		pgrepo.findBySexe(Sexe.FEMININ).forEach(personne-> {
			System.out.println(personne);
		});
		
		testBureauEmployeeRelationShip();
		
	}
	

	private void testBureauEmployeeRelationShip() {
		Employee e = new Employee();
		e.setId(1L);
		e.setNom("Seb");
		emrepo.save(e);
		
		e = new Employee();
		e.setId(2L);
		e.setNom("Seb2");
		emrepo.save(e);
		
		Bureau b = new Bureau();
		b.setId(1L);
		b.setPosition("b1");
		
		
		e.setBureau(brepo.save(b));
		emrepo.save(e);
		
		Employee c = emrepo.findById(2L).get();

		if(c.getBureau() == null) {
			System.out.println("null bureau");
			
		}
		
		Bureau ba = brepo.findById(c.getBureau().getId()).get();
		System.out.println(ba);
		
		System.out.println(c);

		brepo.delete(ba);
		
	}
	
	private Personne generatePersonneWithAdresse() {
		
		Personne p = generatePersonne();
		
		p.setAdresse(generateAdresse());
		
		return p;
	}
	
	private Personne generatePersonne() {
		String prenom = faker.name().lastName();
		String nom = faker.name().firstName();
		
		int rsexe = new Random().nextInt(2);
		
		Sexe sexe = (rsexe == 0)?Sexe.MASCULIN:Sexe.FEMININ;
		Personne p = new Personne(nom,prenom,sexe);
		
		return p;
	}
	
	private Adresse generateAdresse() {
		String rue = faker.address().streetName();
		Integer no = Integer.parseInt(faker.address().buildingNumber());
		
		Adresse adresse = new Adresse(rue,no);
		
		return adresse;
	}
}
