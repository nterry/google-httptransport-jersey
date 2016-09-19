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

  /**
   * Creates an instance with a default {@link Client}.
   */
  public SunJerseyHttpTransport() {
    this(Client.create());
  }

  /**
   * Creates an instance with the given {@link Client}.
   *
   * @param client The {@link Client} to use for requests.
   * @throws IllegalArgumentException if the provided {@link Client} is null
   */
  public SunJerseyHttpTransport(Client client) {
    this.client = validateClient(client);
  }

  private static Client validateClient(Client clientToValidate) {
    if (null != clientToValidate) {
      return clientToValidate;
    }

    throw new IllegalArgumentException("Client cannot be null!");
  }

  @Override
  protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
    WebResource webResource = client.resource(url);

    return new SunJerseyHttpRequest(method, webResource);
  }
}
