const canvasGraphGrid = document.getElementById("canvas_graph_grid");
const canvasGraphFigures = document.getElementById("canvas_graph_figures");
const canvasGraphPoints = document.getElementById("canvas_graph_points");

const gridCtx = canvasGraphGrid.getContext("2d");
const figuresCtx = canvasGraphFigures.getContext("2d");
const pointsCtx = canvasGraphPoints.getContext("2d");

//size of graph
const canvasGraphGridWidth = canvasGraphGrid.clientWidth;
const canvasGraphGridHeight = canvasGraphGrid.clientHeight;

// Main axes
const xAxis = canvasGraphGridWidth / 2;
const yAxis = canvasGraphGridHeight / 2;

// size of grid
const scaleX = 45;
const scaleY = 45;

// coordinate for sending, when click on graph
let xCoordinate;
let yCoordinate;

const urlParams = new URLSearchParams(window.location.search);
const arrayPoints = JSON.parse(decodeURIComponent(urlParams.get('arrayPoint')));

//grid rendering
function gridRendering() {
    let k = 1;

    gridCtx.beginPath();
    gridCtx.strokeStyle = "#000000";
    gridCtx.lineWidth = 0.25;

    for (let i = xAxis; i < canvasGraphGridWidth; i += scaleX) {
        gridCtx.moveTo(i, 0);
        gridCtx.lineTo(i, canvasGraphGridWidth);

        gridCtx.moveTo(i - scaleX * k, 0);
        gridCtx.lineTo(i - scaleX * k, canvasGraphGridWidth);

        gridCtx.moveTo(0, i);
        gridCtx.lineTo(canvasGraphGridHeight, i);

        gridCtx.moveTo(0, i - scaleY * k);
        gridCtx.lineTo(canvasGraphGridHeight, i - scaleY * k);
        k += 2;
    }

    gridCtx.stroke();
    gridCtx.closePath();
}


//drawing main axes
function drawingMainAxes() {
    gridCtx.beginPath();
    gridCtx.strokeStyle = "#000000";
    gridCtx.lineWidth = 1;

    gridCtx.moveTo(xAxis, 0);
    gridCtx.lineTo(xAxis, canvasGraphGridHeight);

    gridCtx.moveTo(0, yAxis);
    gridCtx.lineTo(canvasGraphGridWidth, yAxis);

    gridCtx.stroke();
    gridCtx.closePath();

    let m = -5;

    gridCtx.beginPath();

    gridCtx.font = "16px Arial";
    gridCtx.globalAlpha = 0.4;

    for (let i = 20; i <= canvasGraphGridWidth; i += scaleX) {
        gridCtx.fillText(m, i, yAxis);
        gridCtx.fillText(-1 * m, xAxis, i);
        m++;
    }

    gridCtx.closePath();
}

//drawing point
function drawPoint(xCoord, yCoord, result) {
    console.log("Draw points:" + xCoord + yCoord + result)
    if (result === "true") {
        pointsCtx.fillStyle = "#01ff7f";
    } else {
        pointsCtx.fillStyle = "#ee294b";
    }
    xCoord = xCoord * scaleX + xAxis;
    yCoord = yCoord * -1 * scaleY + yAxis;
    pointsCtx.beginPath();
    pointsCtx.globalAlpha = 1;
    pointsCtx.arc(xCoord, yCoord, 3, 0, 2 * Math.PI);
    pointsCtx.fill();
    pointsCtx.globalAlpha = 0.5;
    pointsCtx.fillStyle = "black";
    pointsCtx.closePath();
}

function drawingFigure(r_value) {

    //drawing square
    figuresCtx.beginPath();
    figuresCtx.strokeStyle = "#000000";
    figuresCtx.globalAlpha = 0.5;
    figuresCtx.fillRect(xAxis, yAxis, r_value * scaleX, r_value * (scaleY / 2));
    figuresCtx.closePath();

    //drawing triangle
    figuresCtx.beginPath();
    figuresCtx.moveTo(xAxis, yAxis);
    figuresCtx.lineTo(xAxis, yAxis + r_value * scaleY);
    figuresCtx.lineTo(xAxis - (r_value * scaleY), yAxis);
    figuresCtx.strokeStyle = "#002636";
    figuresCtx.globalAlpha = 0.5;
    figuresCtx.fill();
    figuresCtx.closePath();

    //drawing quadrant of a circle
    figuresCtx.beginPath();
    figuresCtx.strokeStyle = "#002636";
    figuresCtx.globalAlpha = 0.5;
    figuresCtx.moveTo(xAxis, yAxis);
    figuresCtx.arc(xAxis, yAxis, r_value * scaleX, 0, (3 * Math.PI) / 2, true);
    figuresCtx.fill();
    figuresCtx.closePath();
}

// to delete figures
function deleteFigures() {
    figuresCtx.beginPath();
    figuresCtx.clearRect(0, 0, canvasGraphGridWidth, canvasGraphGridHeight);
    figuresCtx.closePath();
    pointsCtx.beginPath();
    pointsCtx.clearRect(0, 0, canvasGraphGridWidth, canvasGraphGridHeight);
    pointsCtx.closePath();

}

// to redraw points
function redrawPoints() {
    console.log("redraw points")
    const tableRows = document.querySelectorAll('.table tbody tr');
    tableRows.forEach(x => {
        console.log(x)
        if (x.childNodes.length > 1) {
            const children = x.children;
            drawPoint(children[0].innerHTML, children[1].innerHTML, children[3].innerHTML);
        }
    });
    console.log("end of redrawing points")
}

window.addEventListener("load", () => {
    gridRendering();
    drawingMainAxes();
    drawingFigure(JSON.parse(decodeURIComponent(urlParams.get('r'))));
    redrawPoints(arrayPoints);
})

document.querySelector('#canvas_graph_points').onmousemove = function (event) {
    event = event || window.event
    xCoordinate = ((event.offsetX - xAxis) / scaleX).toFixed(2);
    yCoordinate = ((-1) * ((event.offsetY - yAxis) / scaleY)).toFixed(2);
}

document.querySelector('#canvas_graph_points').onclick = function () {
    const xInput = document.querySelector('.x-value');
    const yInput = document.querySelector('.y-input');
    const submitButton = document.querySelector('.submit_button');

    xInput.value = xCoordinate;
    yInput.value = yCoordinate;

    sendTimeToServer();

    submitButton.click();
}
