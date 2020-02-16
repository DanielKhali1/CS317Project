
//Require that js module express is in the program
var express = require('express');
var app = express();
var server = app.listen(80, '192.168.133.1');

app.use(express.static('public'));

var socket = require('socket.io');

var io = socket(server);
// io.set('origins', '0.0.0.0:80');

io.sockets.on('connection', newConnection);

function newConnection(socket)
{
  console.log("New Connection: " + socket.id);


  socket.on('position', mouseMsg);

  function mouseMsg(data)
  {

      socket.broadcast.emit('position', data);

    // console.log(data);
  }

  // socket.on('alive', generate)
  // function generate(data)
  // {
  //   if(!socket.sentMyData)
  //   {
  //   socket.broadcast.emit('alive', data);
  //   // console.log(data);
  //   socket.sentMyData = true;
  // }
  // }

}

console.log("MY SOCKET SERVER IS RUNNING");
