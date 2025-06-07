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
 function openBookingWindow(movieId) {
      const url = '/movie/' + movieId + '/chooseDate'
      const width = 400;
          const height = 600;

          const left = (window.screen.width - width) / 2;
          const top = (window.screen.height - height) / 2;

          const options = [
              `width=${width}`,
              `height=${height}`,
              `left=${left}`,
              `top=${top}`,
              'resizable=no',
              'scrollbars=no',
              'toolbar=no',
              'menubar=no',
              'location=no',
              'status=no'
          ].join(',');

         let params = `scrollbars=no,resizable=no,status=no,location=no,toolbar=no,menubar=no,
         width=${width},height=${height},left=${left},top=${top}`;


          // Открытие окна
          return window.open(url, 'bookingWindow', params);
  }