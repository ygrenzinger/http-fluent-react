'use strict';

$(document).ready(function() {
    renderBug($('#stock-market').get(0));
    $.get('/stocks', function( data ) {
        //renderStocks(data, 'symbol', $('#stock-market').get(0));
    });
});