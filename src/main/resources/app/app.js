'use strict';

var renderReact = function(values, symbol){
    return React.renderComponentToString(StocksComponent({stocks: JSON.parse(values), orderBy:symbol}));
};