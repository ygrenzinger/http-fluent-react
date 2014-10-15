package ygrenzinger.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ygrenzinger.models.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ygrenzinger on 15/10/2014.
 */
public class JsonMapper {

    private ObjectMapper mapper = new ObjectMapper();

    public List<Stock> convertJsonToStocks(String json) {
        List<Stock> stocks  = new ArrayList<>();
        try {
            stocks = mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, Stock.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stocks;
     }

    public String convertStocksToJson(List<Stock> stocks) {
        StringWriter stringWriter = new StringWriter();

        try {
            mapper.writeValue(stringWriter, stocks);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();

    }
}
