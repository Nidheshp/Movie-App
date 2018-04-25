package com.qa.business.service;

import javax.inject.Inject;

import com.qa.business.repository.IMovieRepository;

public class MovieService implements IMovieService {
	
	@Inject
	private IMovieRepository repo;
	
	@Override
	public String getAllMovies() {
		
		return repo.getAllMovies();
	}

}
