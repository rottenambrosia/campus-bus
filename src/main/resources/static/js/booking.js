/**
 * KGEC Campus Bus — Booking Form Enhancements
 * Live fare calculation based on selected stops.
 */
document.addEventListener('DOMContentLoaded', function () {
    const boardingSelect = document.getElementById('boardingStopId');
    const destinationSelect = document.getElementById('destinationStopId');
    const fareFrom = document.getElementById('fare-from');
    const fareTo = document.getElementById('fare-to');
    const fareStops = document.getElementById('fare-stops');
    const fareAmount = document.getElementById('fare-amount');

    // Only run on booking page
    if (!boardingSelect || !destinationSelect) return;

    function updateFare() {
        const boardingOption = boardingSelect.selectedOptions[0];
        const destOption = destinationSelect.selectedOptions[0];

        if (!boardingOption || !destOption ||
            !boardingOption.value || !destOption.value) {
            if (fareFrom) fareFrom.textContent = '—';
            if (fareTo) fareTo.textContent = '—';
            if (fareStops) fareStops.textContent = 'Select stops to see fare';
            if (fareAmount) fareAmount.textContent = '₹ —';
            return;
        }

        const boardingOrder = parseInt(boardingOption.dataset.order) || 0;
        const destOrder = parseInt(destOption.dataset.order) || 0;
        const numStops = Math.abs(boardingOrder - destOrder);
        const fare = Math.max(numStops * 5, 10);

        if (fareFrom) fareFrom.textContent = boardingOption.text;
        if (fareTo) fareTo.textContent = destOption.text;
        if (fareStops) fareStops.textContent = numStops + ' stop' + (numStops !== 1 ? 's' : '');
        if (fareAmount) fareAmount.textContent = '₹' + fare;

        // Validation: same stop
        if (boardingOption.value === destOption.value) {
            if (fareStops) fareStops.textContent = '⚠️ Same stop selected';
            if (fareAmount) fareAmount.textContent = '₹ —';
        }
    }

    boardingSelect.addEventListener('change', updateFare);
    destinationSelect.addEventListener('change', updateFare);
});
