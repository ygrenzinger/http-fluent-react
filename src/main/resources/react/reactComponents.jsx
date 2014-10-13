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
          <div className="price">{this.props.stock.price}$</div>
          <div className="variation">{this.props.stock.variation}</div>
        </div>
      </div>
    );
  }
});

var StocksComponent = React.createClass({
  displayName: 'StocksComponent',
  render: function() {
    var stocks = this.props.stocks;
    stocks = _.sortBy(stocks, this.props.orderBy).reverse();
    var rows = [];
    if (stocks) {
      rows = stocks.map(function(stock) {

        var clickHandler = function(ev) {
          //console.log("Still in reactJs");
          //console.log(ev);
        };

        return (
          <StockComponent stock={stock} />
        );
      });
    }

    return (
      <div className="stocks">
        {
          rows
        }
      </div>
    )
  }
});
