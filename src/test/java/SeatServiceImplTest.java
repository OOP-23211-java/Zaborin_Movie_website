
import com.app.moviebooking.model.Seat;
import com.app.moviebooking.repository.SeatRepository;
import com.app.moviebooking.service.SeatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatServiceImpl seatService;

    private Seat seatToBook;

    @BeforeEach
    void setUp() {
        seatToBook = new Seat();
        seatToBook.setId(20L);
        seatToBook.setBooked(false);
    }

    @Test
    @DisplayName("bookSeat: при существующем месте помечает booked=true и сохраняет")
    void bookSeat_existingSeat_setsBookedAndSaves() {
        when(seatRepository.findById(20L)).thenReturn(Optional.of(seatToBook));
        when(seatRepository.save(any(Seat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Seat result = seatService.bookSeat(20L);

        assertThat(result.isBooked()).isTrue();
        // Проверяем, что сам объект seatToBook тоже получил booked=true
        assertThat(seatToBook.isBooked()).isTrue();

        verify(seatRepository, times(1)).findById(20L);
        verify(seatRepository, times(1)).save(seatToBook);
    }

    @Test
    @DisplayName("bookSeat: несуществующее место – кидает RuntimeException")
    void bookSeat_notFound_throwsException() {
        when(seatRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> seatService.bookSeat(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Seat with id=" + 999 + " not found");

        verify(seatRepository, times(1)).findById(999L);
        verify(seatRepository, never()).save(any());
    }
}
