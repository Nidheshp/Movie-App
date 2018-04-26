package com.qa.business.repository;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import org.apache.log4j.Logger;

import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;

public class MovieDBRepository implements IMovieRepository {

	private static final Logger LOGGER = Logger.getLogger(MovieDBRepository.class);
	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Inject
	private JSONUtil util;

	@Override
	public String getAllMovies() {
		LOGGER.info("MovieDBRepository getAllMovies");

		Query query = manager.createQuery("Select n FROM Movie n");
		Collection<Movie> movies = (Collection<Movie>) query.getResultList();
		return util.getJSONForObject(movies);
	}

	@Override
	public String getAMovie(Long id) {
		Movie aMovie = getMovie(id);
		if (aMovie != null) {
			return util.getJSONForObject(aMovie);
		}

		else {

			return "{\"response\":\"movie not found\"}";
		}
	}

	private Movie getMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	@Override
	@Transactional(REQUIRED)
	public String createAMovie(String movieJSON) {
		Movie aMovie = util.getObjectForJSON(movieJSON, Movie.class);
		manager.persist(aMovie);
		return "{\"response\":\"movie created\"}";

	}

	@Override
	@Transactional(REQUIRED)
	public String deleteAMovie(Long id) {
		Movie oldMovie = getMovie(id);
		if (oldMovie != null) {
			manager.remove(oldMovie);
			return "{\"response\":\"movie deleted\"}";
		}

		else {

			return "{\"Response\": \"Account not found so unable to delete\"}";
		}
	}

	@Override
	@Transactional(REQUIRED)
	public String updateAMovie(String updateTheMovie) {
		Movie updateMovie = util.getObjectForJSON(updateTheMovie, Movie.class);
		Movie originalMovie = getMovie(updateMovie.getId());
		if (updateTheMovie != null) {
			originalMovie = updateMovie;
			manager.merge(originalMovie);

			return "{\"response\":\"Account updated\"}";
		}

		else {

			return "{\"Response\":\"Account not found so unable to update\"}";
		}
	}
}