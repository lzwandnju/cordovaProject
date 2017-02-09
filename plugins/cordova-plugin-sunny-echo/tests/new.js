
function success(str){
alert(str);
}

function error(str){
alert(str);
}
document.getElementById("demo").onclick=function(){
window.cordova.plugins.echo.coolMethod1("Demo",success,error);
};

document.getElementById("demo1").onclick=function(){
window.cordova.plugins.echo.coolMethod2("",success,error);
};

document.getElementById("demo2").onclick=function(){
window.cordova.plugins.echo.coolMethod3("hahaha",success,error);
};