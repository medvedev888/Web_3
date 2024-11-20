
function validateParameters() {
    const xInput = document.querySelector('.x-value');
    const yInput = document.querySelector('.y-input');
    const rInput = document.querySelector('.r-value');

    if (!xInput || !yInput || !rInput) {
        console.error("Could not find input fields.");
        return false;
    }

    const x = parseFloat(xInput.value);
    const y = parseFloat(yInput.value);
    const r = parseFloat(rInput.value);

    let isValid = true;

    if (isNaN(x) || x < -4 || x > 4) {
        alert("X must be between -4 and 4.");
        isValid = false;
    } else if (isNaN(y) || y < -5 || y > 5) {
        alert("Y must be between -5 and 5.");
        isValid = false;
    } else if (isNaN(r) || r < 2 || r > 5) {
        alert("R must be between 2 and 5.");
        isValid = false;
    }

    return isValid;
}
