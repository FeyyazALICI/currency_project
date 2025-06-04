fetch('auxHtml/ts.html')
  .then(res => res.text())
  .then(html => {
    document.getElementById('tsHtml').innerHTML = html;

    const script = document.createElement('script');
    script.src = '../js/auxJs/ts.js';
    script.onload = () => {
      if (typeof initPayloadEvents === 'function') {
        initPayloadEvents(); // ✅ this is what connects the date pickers
      }
    };

    document.body.appendChild(script);
  })
  .catch(err => console.error('Failed to load tsHtml.html:', err));