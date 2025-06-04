let base = '';
let targetCurrency = '';
let dateSelected = '';

// Entry point to initialize after HTML injection
function initHistoricalWithTargeted() {
  const dateInput = document.getElementById("datePicker2");
  if (dateInput) {
    dateInput.addEventListener("change", function () {
      dateSelected = this.value;
      updatePayloadDisplay();
    });
  }
}

// Update base value
function updatePayloadForHistoricalWithBaseWithTarget(selectedBase) {
  base = selectedBase;
  updatePayloadDisplay();
}

// Update target value
function target(selectedTarget) {
  targetCurrency = selectedTarget;
  updatePayloadDisplay();
}

// Update payload display and enable button if all values are selected
function updatePayloadDisplay() {
  const payloadElement = document.getElementById("current-payload-for-historical-targeted");
  const getDataButton = document.getElementById("getCurrencyWithBaseWithDateWithTarget");

  if (payloadElement && getDataButton) {
    if (base && dateSelected && targetCurrency) {
      const payload = {
        currency: base,
        date: dateSelected,
        target: targetCurrency
      };
      payloadElement.textContent = JSON.stringify(payload);
      getDataButton.disabled = false;
    } else {
      payloadElement.textContent = `{ "currency": "${base}" }`;
      getDataButton.disabled = true;
    }
  }
}
