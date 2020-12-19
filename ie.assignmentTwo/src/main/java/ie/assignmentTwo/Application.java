package ie.assignmentTwo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ie.assignmentTwo.daos.IDirectorDao;
import ie.assignmentTwo.daos.IFilmDao;
import ie.assignmentTwo.entities.Director;
import ie.assignmentTwo.entities.Film;
import ie.assignmentTwo.services.FilmService;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	IDirectorDao directorDao;
	@Autowired
	IFilmDao filmDao;
	@Autowired
	FilmService filmService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Populating Database with sample data
		Director director1 = directorDao.save(new Director("Spielberg"));
		filmDao.save(new Film("Jaws", 1975, director1));
		filmDao.save(new Film("Jurassic Park", 1993, director1));
		filmDao.save(new Film("Saving Private Ryan", 1998, director1));
		
		Director director2 = directorDao.save(new Director("Hitchcock"));
		filmDao.save(new Film("Psycho", 1960, director2));
		filmDao.save(new Film("Rear Window", 1963, director2));
		filmDao.save(new Film("Vertigo", 1958, director2));
		
		Director director3 = directorDao.save(new Director("Tarantino"));
		filmDao.save(new Film("Pulp Fiction", 1994, director3));
		filmDao.save(new Film("Death Proof", 2007, director3));
		filmDao.save(new Film("Jackie Brown", 1997, director3));
		
		Director director4 = directorDao.save(new Director("Lynch"));
		filmDao.save(new Film("Dune", 1984, director4));
		filmDao.save(new Film("The Elephant Man", 1980, director4));
		filmDao.save(new Film("Lost Highway", 1997, director4));
		
		Director director5 = directorDao.save(new Director("Nolan"));
		filmDao.save(new Film("Inception", 2010, director5));
		filmDao.save(new Film("Interstellar", 2014, director5));
		filmDao.save(new Film("Dunkirk", 2017, director5));
		
		Director director6 = directorDao.save(new Director("Eastwood"));
		filmDao.save(new Film("Unforgiven", 1992, director6));
		filmDao.save(new Film("Gran Torino", 2008, director6));
		filmDao.save(new Film("Dirty Harry", 1971, director6));
		
		Director director7 = directorDao.save(new Director("Burton"));
		filmDao.save(new Film("Edward Scissorhands", 1990, director7));
		filmDao.save(new Film("Corpse Bride", 2005, director7));
		filmDao.save(new Film("Batman", 1989, director7));
		
		Director director8 = directorDao.save(new Director("Allen"));
		filmDao.save(new Film("Annie Hall", 1977, director8));
		filmDao.save(new Film("Manhattan", 1979, director8));
		filmDao.save(new Film("Blue Jasmine", 2013, director8));
		
		Director director9 = directorDao.save(new Director("Anderson"));
		filmDao.save(new Film("The Master", 2012, director9));
		filmDao.save(new Film("Inherent Vice", 2014, director9));
		filmDao.save(new Film("Hard Eight", 1996, director9));
		
		Director director10 = directorDao.save(new Director("Lee"));
		filmDao.save(new Film("Malcolm X", 1992, director10));
		filmDao.save(new Film("Inside Man", 2006, director10));
		filmDao.save(new Film("Crooklyn", 1994, director10));
		



	}
}
