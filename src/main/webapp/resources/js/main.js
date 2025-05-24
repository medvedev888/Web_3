// действия после изменения параметра r
function handleSliderChange() {
    const r = parseFloat(document.getElementById("pointForm:rValue").value);

    deleteFigures();
    drawingFigure(r);
    redrawPoints();
}

// подготовка параметра r к отправке на бэкэнд
function updatePointsFromSlider() {
    var r = parseFloat(document.getElementById("pointForm:rValue").value);

    if (validateParameters()) {
        updatePointsFromSliderCommand({r: r});
        redrawPoints();
    }

}

function sendTimeToServer() {
    const now = new Date().toISOString();
    sendClientTimeToServer([{name: 'pointBean.clientTime', value: now}]);
}


