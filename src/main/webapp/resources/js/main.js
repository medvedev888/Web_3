function handleSliderChange() {
    const r = parseFloat(document.getElementById("pointForm:rValue").value);

    deleteFigures();
    drawingFigure(r);
    // TODO: need to change this block
    redrawingPoints();

}

function updatePointsFromSlider() {
    var r = parseFloat(document.getElementById("pointForm:rValue").value);
    console.log("r == " + r);
    updatePointsFromSliderCommand({r:r});
}
