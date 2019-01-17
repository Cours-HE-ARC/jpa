package ch.hearc.jpa.entite;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bureau {

	@Id
	private Long id;
	
	private String position;
	
	@Override
	public String toString() {
		return "Bureau [id=" + id + ", position=" + position + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
}
