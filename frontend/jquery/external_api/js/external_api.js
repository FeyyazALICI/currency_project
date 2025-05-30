function updatePayloadForLatestWithBase(currency) {
  const payload = { currency: currency };
  document.getElementById('current-payload-for-latest').textContent = JSON.stringify(payload);
}



function isCurrencySelected(payloadText) {
  try {
    const payload = JSON.parse(payloadText);
    return !!(payload.currency && payload.currency.trim() !== "");
  } catch (err) {
    console.error("Invalid JSON in payload:", err);
    return false;
  }
}


function getExcelData(urlReceived, payloadElementId) {
  const payloadElement = document.getElementById(payloadElementId);

  if (!payloadElement) {
    alert("Error: Payload element not found.");
    return;
  }

  const payloadText = payloadElement.textContent;

  if (!isCurrencySelected(payloadText)) {
    alert("Error: Please select a base currency (USD, EUR, GBP, or CNY) first.");
    return;
  }

  let payload;
  try {
    payload = JSON.parse(payloadText);
  } catch (err) {
    console.error("Invalid payload format:", err);
    alert("Invalid payload format. Please try again.");
    return;
  }

  fetch(urlReceived, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Server responded with status ${response.status}`);
      }
      // Instead of json(), get blob() because it's an Excel file
      return response.blob();
    })
    .then(blob => {
      // Create a download link for the Excel file
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;

      // Set a default filename; you can customize this if your server sends Content-Disposition header
      a.download = 'currency_data_latest.xlsx';

      document.body.appendChild(a);
      a.click();
      a.remove();

      window.URL.revokeObjectURL(url);

      alert("Currency Excel file downloaded successfully!");
    })
    .catch(error => {
      console.error("Error fetching currency data:", error);
      alert("Error fetching currency data. See console for details.");
    });
}



fetch('auxHtml/baseHtml.html')
  .then(res => res.text())
  .then(html => {
    document.getElementById('baseHtml').innerHTML = html;

    // After HTML is injected, dynamically run the JS
    const scriptBase = document.createElement('script');
    scriptBase.src = '../js/auxJs/base.js';

    // Wait for script to load, then initialize manually (if needed)
    scriptBase.onload = () => {
      if (typeof initHistoricalWithBase === 'function') {
        initHistoricalWithBase();
      }
    };

    document.body.appendChild(scriptBase);
  })
  .catch(err => console.error('Failed to load baseHtml.html:', err));



fetch('auxHtml/baseDateHtml.html')
  .then(res => res.text())
  .then(html => {
    document.getElementById('baseDateHtml').innerHTML = html;

    // After HTML is injected, dynamically run the JS
    const scriptBaseDate = document.createElement('script');
    scriptBaseDate.src = '../js/auxJs/external_api_historical_with_base.js';

    // Wait for script to load, then initialize manually (if needed)
    scriptBaseDate.onload = () => {
      if (typeof initHistoricalWithBase === 'function') {
        initHistoricalWithBase();
      }
    };

    document.body.appendChild(scriptBaseDate);
  })
  .catch(err => console.error('Failed to load baseDateHtml.html:', err));





fetch('auxHtml/baseDateTargetHtml.html')
  .then(res => res.text())
  .then(html => {
    document.getElementById('baseDateTargetHtml').innerHTML = html;

    // After HTML is injected, dynamically run the JS
    const script = document.createElement('script');
    script.src = '../js/auxJs/external_api_historical_with_targeted.js';

    // Wait for script to load, then initialize manually (if needed)
    script.onload = () => {
      if (typeof initHistoricalWithTargeted === 'function') {
        initHistoricalWithTargeted();
      }
    };

    document.body.appendChild(script);
  })
  .catch(err => console.error('Failed to load baseDateTargetHtml.html:', err));


  fetch('auxHtml/currencyHtml.html')
  .then(res => res.text())
  .then(html => {
    document.getElementById('currencyHtml').innerHTML = html;

    // After HTML is injected, dynamically run the JS
    const script2 = document.createElement('script');
    script2.src = '../js/auxJs/external_api_currency_description.js';

    // Wait for script to load, then initialize manually (if needed)
    script2.onload = () => {
      if (typeof initHistoricalWithTargeted === 'function') {
        initHistoricalWithTargeted();
      }
    };

    document.body.appendChild(script2);
  })
  .catch(err => console.error('Failed to load baseDateTargetHtml.html:', err));




