'use strict';

$(document).ready(function() {
    $.get('/stocks', function( data ) {
        renderStocks(data, $('#stock-market').get(0));
    });
});