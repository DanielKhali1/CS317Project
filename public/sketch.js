var socket;

let player;
let left = false;
let right = false;

let length = 10;

let cli = [];

let friend1x = -100;
let friend1y = -100;
let friend1id = "0";
let color;
let bg;

let name;

let canJump = false;


let identifier;
x = false;

function setup() {
  bg = loadImage('assets/background.jpg');
  color = [random(0, 150), random(0, 150), random(0, 150)]
  for(i = 0; i < length; i++)
  {
    tupe = [-100, -100, "0", color, "hh" ];
    cli.push(tupe);
  }

  names = ["Caboose", "Ugly", "Man", "Dog", "1337", "Uppity", "Bolas", "Roger"];
  name = names[floor(random(0, 7))];
  console.log(floor(random(0,7)));
  name = prompt("NAME?");


  identifier = random(10);
  createCanvas(800, 800);
  background(0);
  player = new Player();
  socket = io.connect('192.168.133.1')
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
  if(keyCode == UP_ARROW && canJump)
  {
    player.vel.y *= 0;
    player.vel.y -= 10;
    canJump = false;
  }

  if(keyCode == DOWN_ARROW)
  {
    player.vel.y += 15;
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
  background(bg);
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
          cli[i][3] = data.clr;
          cli[i][4] = data.n;
          break;
        }
      }
    }
  }
//  text(parseFloat(2.0002), 20, 20);
  for(i = 0; i < length; i++)
  {
    // console.log(cli[i][0], cli[i][1], cli[i][2]);
    fill(cli[i][3]);
    textSize(25);
    text(cli[i][4], cli[i][0]- 20, cli[i][1] - 10);

    rect(cli[i][0], cli[i][1], player.diameter, player.diameter*2);
    fill(255);
    fill(50);
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
    this.diameter = 30;
    this.acc = createVector(0, 0);
    this.vel = createVector(0, 0);
    this.pos = createVector(random(width),random(height));
  }
  update()
  {
      this.acc.y = 0.3;
      this.vel.add(this.acc);
      this.pos.add(this.vel);

      if(this.vel.y > 0)
      {
        if (this.pos.x < 50 + 200 &&
           this.pos.x + this.diameter > 50 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.y = 500 - this.diameter*2;
          this.vel.y *= 0;
          canJump = true;

        }

        else if (this.pos.x < 300 + 200 &&
           this.pos.x + this.diameter > 300 &&
           this.pos.y < 350 + 55 &&
           this.pos.y + this.diameter*2 > 350)
        {
          this.pos.y = 350 - this.diameter*2;
          this.vel.y *= 0;
          canJump = true;

        }

        else if (this.pos.x < 550 + 200 &&
           this.pos.x + this.diameter > 550 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.y = 500 - this.diameter*2;
          this.vel.y *= 0;
          canJump = true;
        }
      }
      if(this.vel.y < 0)
      {
        if (this.pos.x < 50 + 200 &&
           this.pos.x + this.diameter > 50 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.y = 500+55;
          this.vel.y *= 0;
        }

        else if (this.pos.x < 300 + 200 &&
           this.pos.x + this.diameter > 300 &&
           this.pos.y < 350 + 55 &&
           this.pos.y + this.diameter*2 > 350)
        {
          this.pos.y = 350 + 55;
          this.vel.y *= 0;
        }

        else if (this.pos.x < 550 + 200 &&
           this.pos.x + this.diameter > 550 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.y = 500 + 55;
          this.vel.y *= 0;
        }
      }
      if(this.pos.y > height)
        this.pos.y = 0;
      if(this.pos.x > width)
          this.pos.x = 0;
      if(this.pos.x < 0)
        this.pos.x = width;
      if(this.pos.y < 0 )
        this.pos.y = height;


      if(left)
      {
        if (this.pos.x-8 < 50 + 200 &&
           this.pos.x-8 + this.diameter > 50 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.x = 50 + 200;
        }

        else if (this.pos.x-8 < 300 + 200 &&
           this.pos.x-8 + this.diameter > 300 &&
           this.pos.y < 350 + 55 &&
           this.pos.y + this.diameter*2 > 350)
        {
          this.pos.x = 300 + 200;

        }

        else if (this.pos.x-8 < 550 + 200 &&
           this.pos.x-8 + this.diameter > 550 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.x = 550 + 200;
        }
        else {
          player.pos.x -= 8;
        }

      }
      if(right)
      {
        if (this.pos.x+8 < 50 + 200 &&
           this.pos.x+8 + this.diameter > 50 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.x = 50-this.diameter;
        }

        else if (this.pos.x+8 < 300 + 200 &&
           this.pos.x+8 + this.diameter > 300 &&
           this.pos.y < 350 + 55 &&
           this.pos.y + this.diameter*2 > 350)
        {
          this.pos.x = 300-this.diameter;

        }

        else if (this.pos.x+8 < 550 + 200 &&
           this.pos.x+8 + this.diameter > 550 &&
           this.pos.y < 500 + 55 &&
           this.pos.y + this.diameter*2 > 500)
        {
          this.pos.x = 550-this.diameter;
        }
        else {
          player.pos.x += 8;
        }

      }

      if(this.vel.y > 15)
      {
        this.vel.y = 15;
      }
      if(this.vel.y < -15)
      {
        this.vel.y = -15;
      }

      var data = {
        x : player.pos.x,
        y : player.pos.y,
        id : identifier,
        clr : color,
        n : name
      };

      socket.emit('position', data);
  }

  display() {

    text(name, this.pos.x- 20, this.pos.y - 10);

    fill(color);
    stroke(255);
    rect(this.pos.x, this.pos.y, this.diameter, this.diameter*2);
    fill(255);
  }
}
