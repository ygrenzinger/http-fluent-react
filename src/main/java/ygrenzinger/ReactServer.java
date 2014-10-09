package ygrenzinger;

import net.codestory.http.WebServer;
import net.codestory.http.templating.Model;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import ygrenzinger.helpers.ReactHelper;

/**
 * Created by ygrenzinger on 08/10/2014.
 */
public class ReactServer {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    private Object js() throws Exception {
            engine.eval("var global = this;");
            InputStream inputStream =
                    ReactServer.class.getResourceAsStream("/META-INF/resources/webjars/react/0.11.2/react.js");
            String reactScript = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
            engine.eval(reactScript);
            engine.eval("var MyComponent = React.createClass({\n" +
                    "  render: function () {\n" +
                    "    return React.DOM.h1(null, 'Hi, ' + this.props.msg)\n" +
                    "  }\n" +
                    "});");

            return engine.eval("React.renderComponentToString(MyComponent({msg: 'World!'}))");
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
