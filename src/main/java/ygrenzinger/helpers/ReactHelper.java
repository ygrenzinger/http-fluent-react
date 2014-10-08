package ygrenzinger.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

/**
 * Created by ygrenzinger on 08/10/2014.
 */
public enum ReactHelper implements Helper<Object> {

    appStoreClassSuffix {
        @Override
        public CharSequence apply(Object context, Options options) throws IOException {
            if (((String) context).contains("google"))
                return "_play";
            else
                return "_ios";
        }

    }
}
