package ygrenzinger;

import net.codestory.http.WebServer;
import net.codestory.http.templating.Model;
import org.apache.commons.io.IOUtils;
import ygrenzinger.helpers.ReactHelper;
import ygrenzinger.models.Stock;
import ygrenzinger.models.StockMarketIndex;
import ygrenzinger.services.JsonMapper;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ygrenzinger on 08/10/2014.
 */
public class ReactServer {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private static JsonMapper jsonMapper = new JsonMapper();

    private final StockMarketIndex stockMarketIndex;

    public ReactServer() {
        stockMarketIndex = new StockMarketIndex(loadJsonSp500());
    }

    private List<Stock> loadJsonSp500() {

        try {
            InputStream dataStream = ReactServer.class.getClassLoader().getResourceAsStream("sp500.json");
            String data = IOUtils.toString(dataStream, Charset.forName("UTF-8"));
            return jsonMapper.convertJsonToStocks(data);

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getJsonSp500() {
        return jsonMapper.convertStocksToJson(stockMarketIndex.stocks());
    }

    private void initJS() throws ScriptException, IOException {
        engine.eval("var global = this;");
        InputStream reactSourceStream =
                ReactServer.class.getResourceAsStream("/META-INF/resources/webjars/react/0.11.2/react.min.js");
        String reactScript = IOUtils.toString(reactSourceStream, Charset.forName("UTF-8"));
        engine.eval(reactScript);

        InputStream lodashSourceStream =
                ReactServer.class.getResourceAsStream("/META-INF/resources/webjars/lodash/2.4.1-6/lodash.min.js");
        String lodashScript = IOUtils.toString(lodashSourceStream, Charset.forName("UTF-8"));
        engine.eval(lodashScript);

        InputStream nashornUtilsStream =
                ReactServer.class.getResourceAsStream("/nashorn/utils.js");
        String nashornUtil = IOUtils.toString(nashornUtilsStream, Charset.forName("UTF-8"));
        engine.eval(nashornUtil);

        InputStream reactComponentStream =
                ReactServer.class.getResourceAsStream("/react/reactComponents.js");
        String reactComponent = IOUtils.toString(reactComponentStream, Charset.forName("UTF-8"));
        engine.eval(reactComponent);

        engine.eval("var ReactServer = Java.type('ygrenzinger.ReactServer');");
        engine.eval("var renderReact = function(values, symbol){ " +
                "return React.renderComponentToString(StocksComponent({stocks: JSON.parse(values), orderBy:symbol}))" +
                "};");
        engine.eval("var print = function(value) { console.log(value) };");
    }

    private Object renderJS() {
        try {
            Invocable invocable = (Invocable) engine;
            invocable.invokeFunction("print", "test");
            String data = getJsonSp500();
            Object result = invocable.invokeFunction("renderReact", data, "symbol");
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static void main(String[] args) throws Exception {
        ReactServer reactServer = new ReactServer();
        reactServer.initJS();
        new WebServer(routes -> routes
                .get("/hello/:name", (context, name) -> "Hello, " + name.toUpperCase() + "!")
                .get("/react", (context) -> Model.of("component", reactServer.renderJS()))
                .get("/sp500", (context) -> reactServer.getJsonSp500())
                .registerHandleBarsHelper(ReactHelper.class)
        ).start(8080);
    }
}
