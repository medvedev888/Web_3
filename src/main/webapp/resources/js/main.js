
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
    console.log("r == " + r);
    updatePointsFromSliderCommand({r:r});
    redrawPoints();
}
