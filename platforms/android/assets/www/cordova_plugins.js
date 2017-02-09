cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-plugin-sunny-echo/www/echo.js",
        "id": "cordova-plugin-sunny-echo.echo",
        "clobbers": [
            "cordova.plugins.echo"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.1",
    "cordova-plugin-sunny-echo": "0.0.1"
}
// BOTTOM OF METADATA
});