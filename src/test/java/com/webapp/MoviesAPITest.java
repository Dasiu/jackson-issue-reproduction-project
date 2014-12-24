package com.webapp;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.webapp.controller.MovieController;
import com.webapp.domain.Movie;
import com.webapp.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.is;

public class MoviesAPITest extends BaseIntegrationTest {
    @Autowired
    private MovieController movieController;

    @Autowired
    private MovieService movieService;
    private Movie[] movies;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(movieController);
        movies = twoSampleMovies();
    }

    @Test
    public void defaultObjectMapperShouldBeAbleToSerializeLocalDateTime() {
                when().
                        get("/movies/" + movies[0].getId())
                .then()
                        .statusCode(200).contentType(ContentType.JSON)
                        .body("createdDate[0]", is(2014))
                        .body("createdDate[1]", is(1))
                        .body("createdDate[2]", is(2))
                        .body("createdDate[3]", is(3))
                        .body("createdDate[4]", is(4));
    }



    private Movie[] twoSampleMovies() {
        Movie batman = new Movie();
        batman.setTitle("Batman");
        batman.setCreatedDate(LocalDateTime.of(2014, 1, 2, 3, 4));
        movieService.save(batman);

        return new Movie[]{batman};
    }
}
