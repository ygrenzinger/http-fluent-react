/** @jsx React.DOM */
var StockComponent = React.createClass({
  displayName: 'StockComponent',
  render: function() {

    var stockClassname = "stock";
    var variation = parseFloat(this.props.stock.variation);
    if (variation < 0) {
      stockClassname  += " down";
    } else if (variation > 0) {
      stockClassname  += " up";
    }

    return (
      <div className={stockClassname}>
        <div className="title">{this.props.stock.company}</div>
        <div className="title">{this.props.stock.sector}</div>
        <div className="title">{this.props.stock.industry}</div>
        <div className="info">
          <div className="symbol">{this.props.stock.symbol}</div>
          <div className="price">{this.props.stock.price.toFixed(2)}$</div>
          <div className="variation">{this.props.stock.variation}</div>
        </div>
      </div>
    );
  }
});

var StocksComponent = React.createClass({
  displayName: 'StocksComponent',
  getInitialState: function() {
    return {stocks: this.props.initialStocks};
  },
  refreshData: function() {
    var component = this;
    $.get('/stocks', function( data ) {
        component.setState({stocks: JSON.parse(data)});
    });
  },
  componentDidMount: function() {
    this.refreshDataInterval = setInterval(this.refreshData, 1000);
  },
  componentWillUnmount: function() {
    clearInterval(this.refreshDataInterval);
  },
  render: function() {
    var sortedStocks = _.sortBy(this.state.stocks, this.props.orderBy).reverse();
    var rows = [];
    if (sortedStocks) {
      rows = sortedStocks.map(function(stock) {
        return (
          <StockComponent stock={stock} />
        );
      });
    }

    var options = ['price', 'variation', 'symbol', 'company'].map(function(opt, i) {
        return <option key={i} value={opt} label={opt}>{opt}</option>;
    });

    return (
    <div>
        <div>
        <span>Sort by</span>
         <select name="orderBy">
            {options}
        </select>
        </div>

        <div className="stocks">
        {
          rows
        }
        </div>
      </div>
    );
  }
});



var BugSelect = React.createClass({
  displayName: 'BugSelect',
  render: function() {

    var options = ['price', 'variation', 'symbol', 'company'].map(function(opt, i) {
        return <option key={i} value={opt} label={opt}>{opt}</option>;
    });

    return (
      <div>
<select value="B">
    <option value="A">Apple</option>
    <option value="B">Banana</option>
    <option value="C">Cranberry</option>
  </select>
      </div>
    );
  }
});

var renderBug = function(domElement) {
    if (typeof window == 'undefined') {
        return React.renderToString(<BugSelect></BugSelect>);
    } else {
        return React.render(<BugSelect></BugSelect>, domElement);
    }

}


var renderStocks = function(values, orderBy, domElement){
    if (typeof window == 'undefined') {
        return React.renderComponentToString(StocksComponent({initialStocks: JSON.parse(values), orderBy:orderBy}));
    } else {
        return React.renderComponent(StocksComponent({initialStocks: JSON.parse(values), orderBy:orderBy}), domElement);
    }
};
