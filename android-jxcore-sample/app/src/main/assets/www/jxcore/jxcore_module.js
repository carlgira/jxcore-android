var wsLocalServer = require("ws_server.js");

var JXCoreModule = {
    callJavaFunction: null,
    init: function(callJavaFunction){
        this.callJavaFunction = callJavaFunction;
        wsLocalServer.init();
        console.log("JXCoreModule Init");
    },
    registerJSFunction: function(){

        var self = this;

        var startServerFunction = new JSFunction("JXCoreModule_startServer", function() {
            wsLocalServer.startServer();
            console.log("JXCoreModule_startServer method");
            self.callJavaFunction("serverStarted");
        } );

        var stopServerFunction = new JSFunction("JXCoreModule_stopServer", function() {
            wsLocalServer.stopServer();
            console.log("JXCoreModule_sttopServer method");
            self.callJavaFunction("serverStopped");
        });

        return [startServerFunction, stopServerFunction];
    }
};

function JSFunction(name, jsFunction){
    this.name = name;
    this.jsFunction =  jsFunction;
}

module.exports = JXCoreModule;