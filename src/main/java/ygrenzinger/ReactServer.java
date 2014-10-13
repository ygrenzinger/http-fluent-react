package ygrenzinger;

import net.codestory.http.WebServer;
import net.codestory.http.templating.Model;
import org.apache.commons.io.IOUtils;
import ygrenzinger.helpers.ReactHelper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by ygrenzinger on 08/10/2014.
 */
public class ReactServer {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private Object js() throws Exception {
        engine.eval("var global = this;");
        InputStream reactSourceStream =
                ReactServer.class.getResourceAsStream("/META-INF/resources/webjars/react/0.11.2/react.min.js");
        String reactScript = IOUtils.toString(reactSourceStream, Charset.forName("UTF-8"));
        engine.eval(reactScript);

        InputStream lodashSourceStream =
                ReactServer.class.getResourceAsStream("/META-INF/resources/webjars/lodash/2.4.1-6/lodash.min.js");
        String lodashScript = IOUtils.toString(lodashSourceStream, Charset.forName("UTF-8"));
        engine.eval(lodashScript);


        InputStream dataStream =
                ReactServer.class.getClassLoader().getResourceAsStream("sp500.json");
        String data = IOUtils.toString(dataStream, Charset.forName("UTF-8"));

        InputStream reactComponentStream =
                ReactServer.class.getResourceAsStream("/react/reactComponents.js");
        String reactComponent = IOUtils.toString(reactComponentStream, Charset.forName("UTF-8"));

        engine.eval("var values = " + data + ";");


        engine.eval(reactComponent);

        return engine.eval("React.renderComponentToString(StocksComponent({stocks: values, orderBy:'symbol'}))");
    }

    public static void main(String[] args) throws Exception {
        ReactServer reactServer = new ReactServer();
        Object jsRendering = reactServer.js();
        new WebServer(routes -> routes
                .get("/hello/:name", (context, name) -> "Hello, " + name.toUpperCase() + "!")
                .get("/react", Model.of("component", jsRendering))
                .registerHandleBarsHelper(ReactHelper.class)
        ).start(8080);
    }
}
