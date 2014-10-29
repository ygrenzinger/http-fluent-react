var System = Java.type("java.lang.System");
var console = {};
console.log = function(value) {
    System.out.println("js log :" + value);
};
console.warn = function(value) {
    System.out.println("js warn :" + value);
};
console.error = function(value) {
    System.out.println("js error :" + value);
};

