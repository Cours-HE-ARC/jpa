package ch.hearc.jpa.entite;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Employee {
	
	@Id
	private Long id;
	
	private String nom;
	
	@OneToOne
	private Bureau bureau;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Bureau getBureau() {
		return bureau;
	}

	public void setBureau(Bureau bureau) {
		this.bureau = bureau;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", nom=" + nom + "]";
	}

	/**
	@Override
	public String toString() {
		return "Employee [id=" + id + ", nom=" + nom + ", bureau=" + bureau + "]";
	}*/
	
	
	
	
	
	

}
