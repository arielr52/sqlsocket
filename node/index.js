var Hapi    = require('hapi');
var server  = new Hapi.Server();

server.connection({ port: 3000 });
var io = require('socket.io')(server.listener);

server.route({
    method: 'GET',
    path: '/',
    handler: function (request, reply) {
        reply('Hello, world!');
    }
});

server.route({
    method: 'GET',
    path: '/{name}',
    handler: function (request, reply) {
        reply('Hello, ' + encodeURIComponent(request.params.name) + '!');
    }
});

io.on("connection", function(socket) {
    socket.emit("Connected");
    socket.on('burp', function(){
        socket.emit('Excuse you!');
    });
});

server.start(function () {
    console.log('Server running at:', server.info.uri);
});