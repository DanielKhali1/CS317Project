var socket;
function setup() {
  createCanvas(800, 800);
  background(0);

  socket = io.connect('http://localhost:3000')
}

function mouseDragged() {
//  console.log("Sending " + mouseX + ", " + mouseY);

  var data = {
    x : mouseX,
    y : mouseY
  };

  socket.emit('mouse', data);
}


function draw() {
  noStroke();
  //ellipse(mouseX, mouseY, 50);

  socket.on('mouse', mouseMsg);
  function mouseMsg(data)
  {
    //socket.broadcast.emit('mouse', data);
    fill(0, 255, 0);
    ellipse(data.x, data.y, 50);
  }


}
