var exec = require('cordova/exec');

exports.getPermission = function(arg0, success, error) {
    exec(success, error, "permiPlugin", "getPermission", [arg0]);
};