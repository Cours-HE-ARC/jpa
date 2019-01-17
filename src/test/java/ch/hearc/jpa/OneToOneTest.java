package ch.hearc.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.javafaker.Faker;

import ch.hearc.jpa.entite.Adresse;
import ch.hearc.jpa.entite.Bureau;
import ch.hearc.jpa.entite.Personne;
import ch.hearc.jpa.repository.AdresseRepository;
import ch.hearc.jpa.repository.BureauRepository;
import ch.hearc.jpa.repository.PersonnePagingSortingRepository;
import ch.hearc.jpa.repository.PersonneRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		  classes = { JpaTestConfig.class, TestUtil.class, Faker.class }, 
		  loader = AnnotationConfigContextLoader.class)
@Transactional
public class OneToOneTest {

	@Autowired
	TestUtil testUtil;
	
	@Autowired
	PersonneRepository prepo;
	
	@Autowired
	PersonnePagingSortingRepository pgrepo;
	
	@Autowired
	AdresseRepository arepo;
	
	@Resource
    private BureauRepository bureauRepo;
     
    @Test
    public void givenStudent_whenSave_thenGetOk() {
        Bureau bureau = new Bureau();
        bureau.setPosition("gauche");
        bureau.setId(1L);
        bureauRepo.save(bureau);
        
        assertTrue(true);
    }
    
    @Test
    public void testPersonneEtAdresseStandardRepo() {
    	
    	persistRandomPersonnes(100);
		
		List<Personne> personnes = new ArrayList<>();
		prepo.findAll().forEach(personnes::add);
		
		assertThat(personnes.size()).isEqualTo(100);
		
		personnes.forEach(personne -> {
			assertThat(personne).isNotNull();
			assertThat(personne.getAdresse()).isNotNull();
		});
    }
    
    
    @Test
    public void testPagingRepository() {
    	int nbrePersonnes  = 100;
    	int taillePage = 12;
    	int nbrePage = nbrePersonnes/taillePage + 1;
    	
    	persistRandomPersonnes(nbrePersonnes);
    	
    	Page<Personne> page = pgrepo.findAll(PageRequest.of(0,12, Sort.by("nom")));
    	assertThat(page.getTotalPages()).isEqualTo(nbrePage);
    	assertThat(page.getSize()).isEqualTo(taillePage);
    	
    	assertThat(page.getContent().size()).isEqualTo(100);
    	page.forEach(System.out::println);
    }    
    
    
    
    private void persistRandomPersonnes(int number) {
    	IntStream.range(0, number).forEach(action->{
			
			Personne p = testUtil.generatePersonne();
			
			prepo.save(p);
			
			Adresse a = testUtil.generateAdresse();
			
			p.setAdresse(arepo.save(a));
			
			prepo.save(p);
			
		});
    }
}
