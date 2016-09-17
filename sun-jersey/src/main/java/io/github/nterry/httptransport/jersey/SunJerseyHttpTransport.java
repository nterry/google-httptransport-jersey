package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;

/**
 * Sub class of {@link HttpTransport} using {@link Client} (Jersey 1.x) as a backend.
 *
 * @author Nicholas Terry
 */
public class SunJerseyHttpTransport extends HttpTransport {

  private final Client client;

  public SunJerseyHttpTransport() {
    this(Client.create());
  }

  public SunJerseyHttpTransport(Client client) {
    this.client = client;
  }

  @Override
  protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
    WebResource webResource = client.resource(url);

    return new SunJerseyHttpRequest(method, webResource);
  }
}
