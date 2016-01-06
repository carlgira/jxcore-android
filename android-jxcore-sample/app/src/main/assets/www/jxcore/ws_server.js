#!/usr/bin/env node
var WebSocketServer = require('websocket').server;
var http = require('http');

var WSLocalServer = {
    serverOn : false,
    server : null, 
    wsServer : null,
    init: function(){

      this.server = http.createServer(function(request, response) {
        console.log((new Date()) + ' Received request for ' + request.url);
        response.writeHead(404);
        response.end();
      });

        this.wsServer = new WebSocketServer({
        httpServer: this.server,
        autoAcceptConnections: false
        });

        this.wsServer.on('request', function(request) {
                if (!this.originIsAllowed(request.origin)) {
                  request.reject();
                  console.log((new Date()) + ' Connection from origin ' + request.origin + ' rejected.');
                  return;
                }

                var connection = request.accept('echo-protocol', request.origin);
                console.log((new Date()) + ' Connection accepted.');
                connection.on('message', function(message) {
                    if (message.type === 'utf8') {
                        console.log('Received Message: ' + message.utf8Data);
                        connection.sendUTF(message.utf8Data);
                    }
                    else if (message.type === 'binary') {
                        console.log('Received Binary Message of ' + message.binaryData.length + ' bytes');
                        connection.sendBytes(message.binaryData);
                    }
                });
                connection.on('close', function(reasonCode, description) {
                    console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
                });
            });
    },
    startServer: function() {
      this.server.listen(59999, function() {
          console.log((new Date()) + ' Server is listening on port 12345');
      });
      this.serverOn = true;
    },
    stopServer : function() {
      this.wsServer.shutDown();
      this.server.close();
      this.serverOn = false;
      console.log((new Date()) + ' Server Shutdown');
    },
    originIsAllowed: function(origin) {
      return true;
    },
    isServerOn: function(){
        return this.serverOn;
    }
};

module.exports = WSLocalServer;




