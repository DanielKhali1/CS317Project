var socket;

let player;
let left = false;
let right = false;

let length = 10;

let cli = [];

let friend1x = -100;
let friend1y = -100;
let friend1id = "0";


let identifier;
x = false;

function setup() {
  for(i = 0; i < length; i++)
  {
    tupe = [-100, -100, "0" ];
    cli.push(tupe);
  }


  identifier = random(10);
  createCanvas(800, 800);
  background(0);
  player = new Player();
  socket = io.connect('10.33.192.125')
}

function mouseClicked()
{
  player.pos.x = mouseX;
  player.pos.y = mouseY;
  player.vel.mult(0);
}

function keyPressed() {
  if (keyCode === LEFT_ARROW)
  {
    left = true;
  }
  if (keyCode === RIGHT_ARROW)
  {
    right = true;
  }
  if(keyCode == UP_ARROW)
  {
    player.vel.y -= 10;
  }
}

function keyReleased()
{
  if (keyCode === LEFT_ARROW)
  {
    left = false;
  }
  if (keyCode === RIGHT_ARROW)
  {
    right = false;
  }
}



function draw() {
  background(0);

  rect(50, 500, 200, 55);
  rect(300, 350, 200, 55);
  rect(550, 500, 200, 55);
  player.update();
  player.display();
  newsock = false;

  socket.on('position', posdata)
  function posdata(data)
  {

    //already has a position?
    //run through each connection
    newsock = true;
    for(i = 0; i < length; i++)
    {
      //if the client's id matches then update position
      if(cli[i][2] == data.id)
      {
        cli[i][0] = data.x;
        cli[i][1] = data.y;
        newsock = false;
        break;
      }
      //if the client doesn't match any id.
    }
    if(newsock)
    {
      for(i = 0; i < length; i++)
      {
        if(cli[i][0] == -100 && cli[i][1] == -100)
        {
          cli[i][0] = data.x;
          cli[i][1] = data.y;
          cli[i][2] = data.id;
          break;
        }
      }
    }
  }
  for(i = 0; i < length; i++)
  {
    //console.log(cli[i][0], cli[i][1], cli[i][2]);
    ellipse(cli[i][0], cli[i][1], player.diameter);

  }

}

class Client
{
  constructor(x, y, id)
  {
    this.x = x;
    this.y = y;
    this.id = id;
  }
}

class Player {

  constructor(){
    this.diameter = 80;
    this.acc = createVector(0, 0);
    this.vel = createVector(0, 0);
    this.pos = createVector(random(width),random(height));
  }
  update()
  {
      this.acc.y = 0.3;
      this.vel.add(this.acc);
      this.pos.add(this.vel);

      if (this.pos.x < 50 + 200 &&
         this.pos.x + this.diameter/2 > 50 &&
         this.pos.y < 500 + 55 &&
         this.pos.y + this.diameter/2 > 500)
      {
        this.pos.y = 500 - this.diameter/2;
        this.vel.y *= 0;
      }

      if (this.pos.x < 300 + 200 &&
         this.pos.x + this.diameter/2 > 300 &&
         this.pos.y < 350 + 55 &&
         this.pos.y + this.diameter/2 > 350)
      {
        this.pos.y = 350 - this.diameter/2;
        this.vel.y *= 0;
      }

      if (this.pos.x < 550 + 200 &&
         this.pos.x + this.diameter/2 > 550 &&
         this.pos.y < 500 + 55 &&
         this.pos.y + this.diameter/2 > 500)
      {
        this.pos.y = 500 - this.diameter/2;
        this.vel.y *= 0;
      }

      if(this.pos.y > height)
        this.pos.y = 0;
      if(this.pos.x > width)
          this.pos.x = 0;
      if(this.pos.x < 0)
        this.pos.x = width;

      if(left)
      {
          player.pos.x -= 8;
      }
      if(right)
      {
          player.pos.x += 8;
      }
      var data = {
        x : player.pos.x,
        y : player.pos.y,
        id : identifier
      };

      socket.emit('position', data);
  }

  display() {
    ellipse(this.pos.x, this.pos.y, this.diameter);
  }
}
