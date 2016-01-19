var wsLocalServer = require("ws_server.js");

var JXCoreModule = {
    callNativeFunction: null,
    init: function(callNativeFunction){
        this.callNativeFunction = callNativeFunction;
        wsLocalServer.init();
        console.log("JXCoreModule Init");
    },
    registerJSFunction: function(){

        var self = this;

        var startServerFunction = new JSFunction("JXCoreModule_startServer", function() {
            wsLocalServer.startServer();
            console.log("JXCoreModule_startServer method");
            self.callNativeFunction("serverStarted");
        } );

        var stopServerFunction = new JSFunction("JXCoreModule_stopServer", function() {
            wsLocalServer.stopServer();
            console.log("JXCoreModule_sttopServer method");
            self.callNativeFunction("serverStopped");
        });

        return [startServerFunction, stopServerFunction];
    }
};

function JSFunction(name, jsFunction){
    this.name = name;
    this.jsFunction =  jsFunction;
}

module.exports = JXCoreModule;
