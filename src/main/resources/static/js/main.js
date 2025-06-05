document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('seats-container');
  if (!container) return;

  // Получаем movieId из URL
  const path = window.location.pathname;
  const movieId = path.split('/')[2];

  // Загружаем места
  fetch(`/api/seats/${movieId}`)
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


  /**
   * Функция открывает в новом маленьком окне страницу бронирования.
   * В качестве параметра передаётся ID фильма (можно использовать при генерации динамической ссылки).
   */
  function openBookingWindow(movieId) {
      // Если у вас есть динамический URL, например: /booking/[id].html или /booking?id=...
      // то можно сформировать ссылку так:
      // const url = '/booking/' + movieId + '.html';
      // В простейшем случае, если вы используете просто статичную booking.html, то:
      //const url = '/seats1.html';
      const url = '/movie/' + movieId + '/seats1'
      // Открываем новое окно шириной 400px, высотой 600px, без тулбаров/меню и статус-бара
      const width = 400;
          const height = 600;

          // Позиционирование по центру экрана
          const left = (window.screen.width - width) / 2;
          const top = (window.screen.height - height) / 2;

          // Параметры окна
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
