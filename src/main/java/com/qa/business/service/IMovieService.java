package com.qa.business.service;

public interface IMovieService {

	String getAllMovies();

	String getAMovie(Long id);

	String createAMovie(String jsonMovie);

	String deleteAMovie(Long id);

}
