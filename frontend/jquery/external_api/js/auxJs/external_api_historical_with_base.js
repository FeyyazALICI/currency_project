



function updatePayloadForHistoricalWithBase(currency) {
  const dateInput = document.getElementById('datePicker');
  const dateValue = dateInput.value || "";  // get date or empty string if none selected
  
  const payload = { "currency": currency, "date": dateValue };
  document.getElementById('current-payload-for-historical').textContent = JSON.stringify(payload);
}

// Update payload also when date changes
document.getElementById('datePicker').addEventListener('change', () => {
  const payloadText = document.getElementById('current-payload-for-historical').textContent;
  let currency = "";
  
  try {
    const payload = JSON.parse(payloadText);
    currency = payload.currency || "";
  } catch {
    // fallback if JSON invalid
  }

  updatePayloadForHistoricalWithBase(currency);
});

document.getElementById('datePicker').addEventListener('change', function () {
  document.getElementById('getCurrencyWithBaseWithDate').disabled = false;
});

$(document).ready(function () {
    $('#datePicker').on('input', function () {
        if ($(this).val()) {
        $('#getCurrencyWithExplanations').prop('disabled', false);
        } else {
        $('#getCurrencyWithExplanations').prop('disabled', true);
        }
    });
});