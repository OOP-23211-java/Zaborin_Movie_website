document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('seats-container');
  const modal = document.getElementById('purchaseModal');
  const form = document.getElementById('purchaseForm');
  const cancelBtn = document.getElementById('cancelBtn');
  let currentSeatId = null;

  if (!container) return;

  // Получаем movieId и дату из URL
  const path = window.location.pathname.split('/');
  const movieId = path[2];
  const date = path[3];

  // Загружаем места
  fetch(`/api/seats/${movieId}/${date}`)
    .then(res => res.json())
    .then(data => renderSeats(data, container));

  // Отмена в модалке
  cancelBtn.addEventListener('click', () => {
    closeModal();
  });

  // Обработка отправки формы
  form.addEventListener('submit', e => {
    e.preventDefault();
    if (!currentSeatId) return;

    // Здесь можно добавить валидацию полей перед отправкой

    fetch(`/api/seats/book/${currentSeatId}`, { method: 'POST' })
      .then(res => res.json())
      .then(updatedSeat => {
        // Деактивируем кнопку и закрываем модалку
        const btn = document.querySelector(`button[data-seat-id="${currentSeatId}"]`);
        if (btn) btn.disabled = true;
        closeModal();
        alert('Место успешно забронировано!'); // Можно заменить на красивый тост
      })
      .catch(err => {
        console.error(err);
        alert('Ошибка при бронировании.');
      });
  });

  function renderSeats(seats, container) {
    container.innerHTML = '';
    seats.forEach(seat => {
      const btn = document.createElement('button');
      btn.textContent = `Ряд ${seat.row} - ${seat.number}`;
      btn.disabled = seat.booked;
      btn.setAttribute('data-seat-id', seat.id);
      btn.addEventListener('click', () => openPurchaseModal(seat.id));
      container.appendChild(btn);
    });
  }

  function openPurchaseModal(seatId) {
    currentSeatId = seatId;
    // Сброс полей формы
    form.reset();
    modal.style.display = 'flex';
  }

  function closeModal() {
    modal.style.display = 'none';
    currentSeatId = null;
  }
});

function openBookingWindow(movieId) {
  const url = '/movie/' + movieId + '/chooseDate';
  const width = 400, height = 600;
  const left = (window.screen.width - width) / 2;
  const top = (window.screen.height - height) / 2;
  const params = [
    `width=${width}`, `height=${height}`,
    `left=${left}`, `top=${top}`,
    'resizable=no', 'scrollbars=no',
    'toolbar=no', 'menubar=no',
    'location=no', 'status=no'
  ].join(',');
  return window.open(url, 'bookingWindow', params);
}
