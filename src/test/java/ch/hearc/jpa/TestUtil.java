package ch.hearc.jpa;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ch.hearc.jpa.entite.Adresse;
import ch.hearc.jpa.entite.Personne;
import ch.hearc.jpa.entite.Sexe;

@Component
public class TestUtil {
	
	@Autowired
	Faker faker;

	public  Personne generatePersonneWithAdresse() {
		
		Personne p = generatePersonne();
		
		p.setAdresse(generateAdresse());
		
		return p;
	}
	
	public  Personne generatePersonne() {
		String prenom = faker.name().lastName();
		String nom = faker.name().firstName();
		
		int rsexe = new Random().nextInt(2);
		
		Sexe sexe = (rsexe == 0)?Sexe.MASCULIN:Sexe.FEMININ;
		Personne p = new Personne(nom,prenom,sexe);
		
		return p;
	}
	
	public  Adresse generateAdresse() {
		String rue = faker.address().streetName();
		Integer no = Integer.parseInt(faker.address().buildingNumber());
		
		Adresse adresse = new Adresse(rue,no);
		
		return adresse;
	}
}
