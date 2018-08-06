
var exec = require('cordova/exec');

var PLUGIN_NAME = 'ModoKiosco';

var ModoKiosco = {
  mensaje: function (name, successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, "mensaje", [name]);
  }
};

module.exports = ModoKiosco;
