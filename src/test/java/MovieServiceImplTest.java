
import com.app.moviebooking.model.Movie;
import com.app.moviebooking.model.Schedule;
import com.app.moviebooking.model.Seat;
import com.app.moviebooking.repository.MovieRepository;
import com.app.moviebooking.service.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movieWithSchedules;
    private Schedule schedule1;
    private Schedule schedule2;
    private Seat seatA;
    private Seat seatB;
    private Seat seatC;

    @BeforeEach
    void setUp() {
        // Подготовка трех мест
        seatA = new Seat();
        seatA.setId(100L);
        seatA.setBooked(false);

        seatB = new Seat();
        seatB.setId(101L);
        seatB.setBooked(true);

        seatC = new Seat();
        seatC.setId(102L);
        seatC.setBooked(false);

        // Первый Schedule с датой "2025-06-10" и двумя местами
        schedule1 = new Schedule();

        schedule1.setDate("2025-06-10");
        schedule1.setSeats(Arrays.asList(seatA, seatB));

        // Второй Schedule с датой "2025-06-11" и одним местом
        schedule2 = new Schedule();
        schedule2.setDate("2025-06-11");
        schedule2.setSeats(Collections.singletonList(seatC));

        // Фильм, содержащий оба расписания
        movieWithSchedules = new Movie();
        movieWithSchedules.setId(5L);
        movieWithSchedules.setTitle("Test Movie");
        movieWithSchedules.setSchedules(Arrays.asList(schedule1, schedule2));
    }

    @Test
    @DisplayName("getAllMovies: репозиторий возвращает непустой список")
    void getAllMovies_returnsList() {
        Movie m1 = new Movie();
        m1.setId(1L);
        m1.setTitle("Film A");
        Movie m2 = new Movie();
        m2.setId(2L);
        m2.setTitle("Film B");

        when(movieRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Movie> result = movieService.getAllMovies();

        assertThat(result).hasSize(2)
                .extracting(Movie::getId)
                .containsExactly(1L, 2L);

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllMovies: репозиторий возвращает пустой список")
    void getAllMovies_emptyList() {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        List<Movie> result = movieService.getAllMovies();

        assertThat(result).isEmpty();
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getMovieById: существующий фильм возвращается корректно")
    void getMovieById_existingId() {
        when(movieRepository.findById(5L)).thenReturn(Optional.of(movieWithSchedules));

        Movie result = movieService.getMovieById(5L);

        assertThat(result).isEqualTo(movieWithSchedules);
        verify(movieRepository, times(1)).findById(5L);
    }


    @Test
    @DisplayName("getScheduleById: возвращает полный список расписаний")
    void getScheduleById_existingId() {
        when(movieRepository.findById(5L)).thenReturn(Optional.of(movieWithSchedules));

        List<Schedule> schedules = movieService.getScheduleById(5L);

        assertThat(schedules).hasSize(2)
                .extracting(Schedule::getDate)
                .containsExactly("2025-06-10", "2025-06-11");

        verify(movieRepository, times(1)).findById(5L);
    }

    @Test
    @DisplayName("getSeatById: при совпадающей дате возвращает места именно из этого расписания")
    void getSeatById_dateMatches() {
        when(movieRepository.findById(5L)).thenReturn(Optional.of(movieWithSchedules));

        // Запрашиваем дату "2025-06-11" – должно вернуть только seatC
        List<Seat> seats = movieService.getSeatById(5L, "2025-06-11");

        assertThat(seats).hasSize(1)
                .containsExactly(seatC);

        verify(movieRepository, times(1)).findById(5L);
    }

    @Test
    @DisplayName("getSeatById: несуществующий фильм – кидает RuntimeException")
    void getSeatById_movieNotFound() {
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.getSeatById(999L, "2025-06-10"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Movie with id=" + 999 + " not found");

        verify(movieRepository, times(1)).findById(999L);
    }
}
