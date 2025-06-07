document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('seats-container');
  if (!container) return;

  // Получаем movieId из URL
  const path = window.location.pathname;
  const movieId = path.split('/')[2];
  // Загружаем места
  fetch(`/api/seats/${movieId}`)
      .then(res => res.json())
      .then(data => renderSeats(data, container, movieId));
});

function renderSeats(schedule, container, movieId) {
  container.innerHTML = '';
  schedule.forEach(schedule => {
    const btn = document.createElement('button');
    btn.textContent = `${schedule.date}`;
    const targetUrl = `/movie/${movieId}/seats1/${schedule.date}`; ///movie/{id}/seats1/{data}

    btn.addEventListener('click', () => {
      window.location.href = targetUrl;
      // Если нужно открыть в новом окне/вкладке:
      // window.open(targetUrl, '_blank');
    });

    container.appendChild(btn);
  });
}



  function openBookingWindow(movieId) {
      const url = '/movie/' + movieId + '/seats1'
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
