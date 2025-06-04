let baseTs = '';
let targetTs = '';
let dateOnset = '';
let dateEnd = '';


// Update base value
function updatePayloadForTsBase(selectedBase, clickedBtn) {
  baseTs = selectedBase;
  updateCurrentPayloadIfReady();

  // Reset other base buttons manually
  const baseButtons = [
    document.getElementById('ts_base_dollar'),
    document.getElementById('ts_base_euro'),
    document.getElementById('ts_base_gbp'),
    document.getElementById('ts_base_cny')
  ];

  baseButtons.forEach(btn => {
    btn.classList.remove('btn-success');
    btn.classList.add('btn-warning');
  });

  // Highlight the clicked one
  clickedBtn.classList.remove('btn-warning');
  clickedBtn.classList.add('btn-success');
}

// Update target value
function targetTsF(selectedTarget, clickedBtn) {
  targetTs = selectedTarget;
  updateCurrentPayloadIfReady();

  // Reset other base buttons manually
  const targetButtons = [
    document.getElementById('ts_target_dollar'),
    document.getElementById('ts_target_euro'),
    document.getElementById('ts_target_gbp'),
    document.getElementById('ts_target_cny')
  ];

  targetButtons.forEach(btn => {
    btn.classList.remove('btn-success');
    btn.classList.add('btn-warning');
  });

  // Highlight the clicked one
  clickedBtn.classList.remove('btn-warning');
  clickedBtn.classList.add('btn-success');
}

function initPayloadEvents() {
  document.getElementById('datePickerTsOnset')?.addEventListener('change', function () {
    dateOnset = this.value;
    updateCurrentPayloadIfReady();
  });

  document.getElementById('datePickerTsEnd')?.addEventListener('change', function () {
    dateEnd = this.value;
    updateCurrentPayloadIfReady();
  });
}

// Payload updater
function updateCurrentPayloadIfReady() {
    const payload = {
        currency: baseTs,
        target: targetTs,
        dateOnSet: dateOnset,
        dateEnd: dateEnd
    };
    document.getElementById('current-payload-for-ts').innerText = JSON.stringify(payload, null, 2);

    // ✅ ENABLE the button if the payload is valid
    const btn = document.getElementById("getDataButtonTs");
    if (btn) {
      btn.disabled = !isPayloadCompleted(JSON.stringify(payload));  // true → disable, false → enable
    }
}


function isPayloadCompleted(payloadText) {
  try {
    const payload = JSON.parse(payloadText);
    return (
      payload.currency && payload.currency.trim() !== "" &&
      payload.target && payload.target.trim() !== "" &&
      payload.dateOnSet && payload.dateOnSet.trim() !== "" &&
      payload.dateEnd && payload.dateEnd.trim() !== ""
    );
  } catch (err) {
    console.error("Invalid JSON in payload:", err);
    return false;
  }
}


function getExcelDataTs(urlReceived, payloadElementId) {
  const payloadElement = document.getElementById(payloadElementId);

  if (!payloadElement) {
    alert("Error: Payload element not found!");
    return;
  }

  const payloadText = payloadElement.textContent;
  if(!isPayloadCompleted(payloadText)){
    alert("Error: Payload is not complete!");
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
      a.download = 'time_specific_data.xlsx';

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