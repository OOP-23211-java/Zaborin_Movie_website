document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('seats-container');
  if (!container) return;

  // Получаем movieId из URL
  const path = window.location.pathname;
  const movieId = path.split('/')[2];
  const date = path.split('/')[3];
  // Загружаем места
  fetch(`/api/seats/${movieId}/${date}`)
      .then(res => res.json())
      .then(data => renderSeats(data, container));
});

function renderSeats(seats, container) {
  container.innerHTML = '';
  seats.forEach(seat => {
      const btn = document.createElement('button');
      btn.textContent = `Row ${seat.row} - ${seat.number}`;
      btn.disabled = seat.booked;
      btn.addEventListener('click', () => bookSeat(seat.id, btn));
      container.appendChild(btn);
  });
}

function bookSeat(seatId, btn) {
  fetch(`/api/seats/book/${seatId}`, { method: 'POST' })
      .then(res => res.json())
      .then(updatedSeat => {
          btn.disabled = true;
          alert('Место забронировано!');
      });
}
