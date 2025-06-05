
import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.MovieRepository;
import com.example.moviebooking.service.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie1;
    private Movie movie2;
    private Seat seatA;
    private Seat seatB;

    @BeforeEach
    void setUp() {
        movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Film A");
        movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Film B");

        seatA = new Seat();
        seatA.setId(10L);
        seatA.setBooked(false);

        seatB = new Seat();
        seatB.setId(11L);
        seatB.setBooked(true);

        List<Seat> seats = Arrays.asList(seatA, seatB);
        movie1.setSeats(seats);
    }

    @Test
    @DisplayName("getAllMovies: репозиторий возвращает список фильмов")
    void getAllMovies_returnsList() {
        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertThat(result).hasSize(2)
                .containsExactly(movie1, movie2);

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getMovieById: существующий фильм возвращается корректно")
    void getMovieById_existingId_returnsMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

        Movie result = movieService.getMovieById(1L);

        assertThat(result).isEqualTo(movie1);
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getMovieById: несуществующий id – кидает RuntimeException")
    void getMovieById_notFound_throwsException() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.getMovieById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Movie not found");

        verify(movieRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("getSeatById: возвращает список мест для существующего фильма")
    void getSeatById_existingId_returnsSeats() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

        List<Seat> seats = movieService.getSeatById(1L);

        assertThat(seats).hasSize(2)
                .containsExactlyElementsOf(movie1.getSeats());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getSeatById: несуществующий id – кидает RuntimeException")
    void getSeatById_notFound_throwsException() {
        when(movieRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.getSeatById(100L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Movie not found");

        verify(movieRepository, times(1)).findById(100L);
    }
}
