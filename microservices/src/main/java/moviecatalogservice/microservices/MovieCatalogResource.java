package moviecatalogservice.microservices;

import moviecatalogservice.microservices.models.CatalogItem;
import moviecatalogservice.microservices.models.Movie;
import moviecatalogservice.microservices.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    public RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog (@PathVariable("userId")  String userId){

        RestTemplate restTemplate= new RestTemplate();
    List<Rating> ratings= Arrays.asList(
            new Rating("1234",4),
            new Rating("5678",5)
    );
        return ratings.stream().map(rating -> {
            Movie movie =restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(),"desc",rating.getRating());
        })
                .collect(Collectors.toList());

   // Collections.singletonList(new CatalogItem("Charlie","good",5));

    }

}
