package ygrenzinger.repositories;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by ygrenzinger on 13/10/2014.
 */
public class OrderRepository {
    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target("http://localhost:9998").path("resource");

    public void getOrder() {

    }
}
