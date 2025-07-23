package com.spring.database.practice.chap01.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MovieRatingRouteController {
    @GetMapping("/movie-rating")
    public String movieRating() {
        return "movie-rating";
    }
}
