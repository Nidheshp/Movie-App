package com.qa.business.repository;

public interface IMovieRepository {

	String getAllMovies();

	String getAMovie(Long id);

	String createAMovie(String movieJSON);

	String deleteAMovie(Long id);

	String updateAMovie(String updateTheMovie);
}
