
// function for drawing figure on graph when selecting the R parameter
function handleSliderChange() {
    const r = parseFloat(document.getElementById("pointForm:rValue").value);

    deleteFigures();
    drawingFigure(r);
    // TODO: need to change this block
    redrawingPoints();

}