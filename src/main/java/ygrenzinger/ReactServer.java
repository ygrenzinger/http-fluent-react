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

public class ReactServer {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private static JsonMapper jsonMapper = new JsonMapper();

    private final StockMarketIndex stockMarketIndex;

    public static int numberOfStocksToDisplay = 150;

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

    public String getJsonStocks(int numberOfStocks) {
        if (numberOfStocks > 500) {
            numberOfStocks = 500;
        }
        return jsonMapper.convertStocksToJson(stockMarketIndex.stocks().subList(0, numberOfStocks-1));
    }

    private void loadAndEvalScript(String src) throws IOException, ScriptException {
        InputStream reactSourceStream =
                ReactServer.class.getResourceAsStream(src);
        String reactScript = IOUtils.toString(reactSourceStream, Charset.forName("UTF-8"));
        engine.eval(reactScript);
    }

    private void initJS() throws ScriptException, IOException {
        engine.eval("var global = this;");

        loadAndEvalScript("/META-INF/resources/webjars/react/0.11.2/react.min.js");
        loadAndEvalScript("/META-INF/resources/webjars/lodash/2.4.1-6/lodash.min.js");
        loadAndEvalScript("/nashorn/utils.js");
        loadAndEvalScript("/app/react/compiled/reactComponents.js");

        engine.eval("var print = function(value) { console.log(value) };");
    }

    private Object renderJS() {
        try {
            Invocable invocable = (Invocable) engine;
            invocable.invokeFunction("print", "rendering react on the server");
            String data = getJsonStocks(numberOfStocksToDisplay);
            Object result = invocable.invokeFunction("renderStocks", data, "symbol", null);
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
                .get("/", (context) -> Model.of("component", reactServer.renderJS()))
                .get("/stocks", (context) -> reactServer.getJsonStocks(numberOfStocksToDisplay))
                .registerHandleBarsHelper(ReactHelper.class)
        ).start(8080);
    }
}
