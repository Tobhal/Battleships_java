window.onload = startup;

let boardSize = [10, 10]; //[y, x]
let boatLength = 5;
let directionValue = 0;

let gameX = 0, gameY = 0;

let boatPlaces = [];

const Direction = {
  UP : [-1, 0],         //[y, x]
  DOWN : [1, 0],        //[y, x]
  LEFT : [0, -1],       //[y, x]
  RIGHT : [0, 1],       //[y, x]

  UP_LEFT : [-1, -1],   //[y, x]
  UP_RIGHT : [-1, 1],   //[y, x]
  DOWN_LEFT : [1, -1],  //[y, x]
  DOWN_RIGHT : [1, 1]   //[y, x]
}

let direction = Direction.UP;

function createElement() {
  var newDiv = document.createElement("div");
  newDiv.classList.add("square")
  newDiv.setAttribute("id", "square1")

  document.getElementById("board").appendChild(newDiv)


}

function createBoard() {
  let board = document.getElementById("board");

  for (let i = 0; i < boardSize[0]; i++) {
    let y = document.createElement("div");
    y.classList.add("squareContainer");
  
    for (let l = 0; l < boardSize[1]; l++) {
      let x = document.createElement("div");
      x.classList.add("square");
      y.appendChild(x);
      
      x.addEventListener("mouseover", function (event) {
          displayBoat(event, l, i);
      })

      x.addEventListener("mouseleave", function (event) {
        //removeDisplayBoat(event, l, i);
        updateBoard();
      })

      x.addEventListener("click", function (event) {
        sendCoordinates(event, l, i)
        placeBoat(l, i)
      })

    }
    board.appendChild(y);
  }
}

function placeBoat(x, y) {
  if (canPlace(x, y)) {
    for (let i = 0; i < boatLength; i++) {
      boatPlaces.push([x + (i * direction[1]), y + (i * direction[0])]);
    }
  }

  console.log(boatPlaces);
}

function updateBoard() {
  for (let i = 0; i < boardSize[0]; i++) {
    let squareContainer = document.getElementsByClassName("squareContainer")[i];
    for (let l = 0; l < boardSize[1]; l++) {
      squareContainer.getElementsByClassName("square")[l].style.backgroundColor = "black";
    }
  }

  boatPlaces.forEach((activity) => {
    let squareContainer = document.getElementsByClassName("squareContainer")[activity[0, 1]];
    squareContainer.getElementsByClassName("square")[activity[0,0]].style.backgroundColor = "red";
  })
}

function displayBoat(event, x, y) {
  updateBoard()
  
  if (canPlace(x, y)) {
    document.getElementsByTagName("body")[0].style.cursor = "pointer";
    for (let i = 0; i < boatLength; i++) {
      let container = document.getElementsByClassName("squareContainer")[y + (i * direction[0])];
      container.getElementsByClassName("square")[x + (i * direction[1])].style.backgroundColor = "gray";
    }
  } else {
    document.getElementsByTagName("body")[0].style.cursor = "no-drop";
  }

  gameX = x;
  gameY = y;
}

function sendCoordinates(event, x, y) {
  console.log("X: " + x + " Y: " + y + " dir: " + getKeyByValue(Direction, direction))
}

function canPlace(x, y) {
  endY = y + (boatLength * direction[0]);
  endX = x + (boatLength * direction[1]);

  if ((endY >= -1 && endY <= boardSize[0]) && (endX >= -1 && endX <= boardSize[1])) {
    for (let i = 0; i < boatLength; i++) {
      let container = document.getElementsByClassName("squareContainer")[y + (i * direction[0])];
      let square = container.getElementsByClassName("square")[x + (i * direction[1])];

      if (square.style.backgroundColor == "red") {
        return false;
      }
    }
    return true;
  } else {
    return false;

  }
  return false;
}

function getKeyByValue(object, value) {
  return Object.keys(object).find(key => object[key] === value);
}

function rotateBoat(e) {
    directionValue++;

    updateBoard();

    switch (directionValue) {
      case 0: direction = Direction.UP; break;
      case 1: direction = Direction.UP_RIGHT; break;
      case 2: direction = Direction.RIGHT; break;
      case 3: direction = Direction.DOWN_RIGHT; break;
      case 4: direction = Direction.DOWN; break;
      case 5: direction = Direction.DOWN_LEFT; break;
      case 6: direction = Direction.LEFT; break;
      case 7: direction = Direction.UP_LEFT; directionValue = -1; break;
      default:
        break;
    }

    displayBoat(e, gameX, gameY);
}

function keyPressed(e) {
  updateBoard()

  switch (e.key) {
    case "r": 
      rotateBoat(e); 
      break;
    case "ArrowUp": 
      boatLength >= 5 ? boatLength : boatLength++; 
      break;
    case "ArrowDown": 
      boatLength <= 1 ? boatLength : boatLength--; 
      break;
  }

  displayBoat(e, gameX, gameY);
}

function startup() {
  console.log("hello world")

  document.addEventListener("keydown", keyPressed)

  createBoard()

  updateBoard()
}

/*
TODO: make so i kan use more boards, use the div board and change that to class, so I can have a attack board and a private board

*/