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
    return {
        stocks: this.props.initialStocks,
        orderBy: 'symbol'
    };
  },
  refreshData: function() {
    var component = this;
    $.get('/stocks', function( data ) {
        component.setState({stocks: JSON.parse(data)});
    });
  },
  handleOrderByChange: function(event) {
    this.setState({orderBy: event.target.value});
  },
  componentDidMount: function() {
    this.refreshDataInterval = setInterval(this.refreshData, 10 * 1000);
  },
  componentWillUnmount: function() {
    clearInterval(this.refreshDataInterval);
  },
  render: function() {
    var sortedStocks = _.sortBy(this.state.stocks, this.state.orderBy).reverse();
    var rows = [];
    if (sortedStocks) {
      rows = sortedStocks.map(function(stock) {
        return (
          <StockComponent stock={stock} />
        );
      });
    }

/* doesn't handle any events :'(
    var options = ['price', 'variation', 'symbol', 'company'].map(function(opt, i) {
        return <span><input type="radio" name="orderBy" value={opt} onChange={this.handleChange}/>{opt}</span>;
    });
*/

    return (
    <div>
        <div>
            <span>Sort by</span>
            <select name="orderBy" onChange={this.handleOrderByChange}>
                /* uncomment this bloc to have this error: TypeError: null is not a function in at line number 6621
                <option value="price">price</option>
                <option value="variation">variation</option>
                <option value="symbol">symbol</option>
                <option value="company">company</option>
                */
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

var renderStocks = function(values, domElement){
    if (typeof window == 'undefined') {
        return React.renderComponentToString(StocksComponent({initialStocks: JSON.parse(values)}));
    } else {
        return React.renderComponent(StocksComponent({initialStocks: JSON.parse(values)}), domElement);
    }
};
