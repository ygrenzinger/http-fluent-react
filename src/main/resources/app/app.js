'use strict';

var idPeriodicRefresh = null;

var launchPeriodicRefresh = function() {
    return window.setInterval(function(){
        $.get('/stocks', function( data ) {
            renderStocks(data, 'symbol', $('#stock-market').get(0));
        });
    }, 1000);
};

var stopPeriodicRefresh = function() {
    window.clearInterval(idPeriodicRefresh);
};


$(document).ready(function() {
    idPeriodicRefresh = launchPeriodicRefresh();
});