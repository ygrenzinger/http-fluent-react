package ygrenzinger.helpers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ygrenzinger on 08/10/2014.
 */
public enum ReactHelper implements Helper<Object> {

    renderReact {

        @Override
        public CharSequence apply(Object context, Options options) throws IOException {
            return new Handlebars.SafeString(context.toString());
        }

  }
}
