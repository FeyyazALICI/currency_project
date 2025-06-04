  function getExcelDataCurrency(apiUrl) {
    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not OK');
        }
        return response.blob(); // Excel file will come as a blob
      })
      .then(blob => {
        // Create a temporary download link
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'currencies.xlsx'; // You can choose the filename
        document.body.appendChild(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url); // Clean up
      })
      .catch(error => {
        console.error('Error downloading the Excel file:', error);
        alert('Failed to download currency data.');
      });
  }