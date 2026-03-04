package com.services;


import com.dtos.MovieDto;
import com.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Value("${movie.api.url}")
    private String movieApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // GET /reviews
    public List<MovieDto> getAllMovies() {
        MovieDto[] movies = restTemplate.getForObject(
                movieApiUrl + "/movies", MovieDto[].class);
        assert movies != null;
        return Arrays.asList(movies);
    }

    // GET /reviews/{id}
    public MovieDto getMovieById(int id) {
        MovieDto mov = new MovieDto();
        mov.setId((long)1);
        mov.setTitre("Avengers");
        return mov;
        //return restTemplate.getForObject( movieApiUrl + "/movies/" + id, MovieDto.class);
    }
}
