
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.SeatRepository;
import com.example.moviebooking.service.SeatServiceImpl;
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
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatServiceImpl seatService;

    private Seat seat1;
    private Seat seat2;

    @BeforeEach
    void setUp() {
        seat1 = new Seat();
        seat1.setId(5L);
        seat1.setBooked(false);
        seat2 = new Seat();
        seat2.setId(6L);
        seat2.setBooked(true);
    }

    @Test
    @DisplayName("getSeatsByMovie: возвращает список мест для фильма")
    void getSeatsByMovie_returnsList() {
        List<Seat> seats = Arrays.asList(seat1, seat2);
        when(seatRepository.findByMovieId(1L)).thenReturn(seats);

        List<Seat> result = seatService.getSeatsByMovie(1L);

        assertThat(result).hasSize(2)
                .containsExactly(seat1, seat2);

        verify(seatRepository, times(1)).findByMovieId(1L);
    }

    @Test
    @DisplayName("bookSeat: существующее место бронируется и сохраняется")
    void bookSeat_existingSeat_setsBookedAndSaves() {
        Seat toBook = new Seat();
        toBook.setId(7L);
        toBook.setBooked(false);

        when(seatRepository.findById(7L)).thenReturn(Optional.of(toBook));
        when(seatRepository.save(any(Seat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Seat result = seatService.bookSeat(7L);

        assertThat(result.isBooked()).isTrue();
        assertThat(toBook.isBooked()).isTrue();

        verify(seatRepository, times(1)).findById(7L);
        verify(seatRepository, times(1)).save(toBook);
    }

    @Test
    @DisplayName("bookSeat: несуществующий seat – кидает RuntimeException")
    void bookSeat_notFound_throwsException() {
        when(seatRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> seatService.bookSeat(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Seat not found");

        verify(seatRepository, times(1)).findById(999L);
        verify(seatRepository, never()).save(any());
    }
}
