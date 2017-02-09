cordova.define("cordova-plugin-sunny-echo.echo", function(require, exports, module) { var exec = require('cordova/exec');
exports.coolMethod1 = function(str, success, error) {
    exec(success, error, "Echo", "coolMethod1", [str]);
};
exports.coolMethod2 = function(str, success, error) {
    exec(success, error, "Echo", "coolMethod2", [str]);
};
exports.coolMethod3 = function(str, success, error) {
    exec(success, error, "Echo", "coolMethod3", [str]);
};

});
