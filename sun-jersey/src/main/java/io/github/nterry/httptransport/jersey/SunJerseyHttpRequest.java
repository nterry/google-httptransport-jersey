package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Sub class of {@link LowLevelHttpRequest} using {@link Client} (Jersey 1.x) as a backend.
 *
 * @author Nicholas Terry
 */
public class SunJerseyHttpRequest extends LowLevelHttpRequest {

  private final String method;
  private final WebResource webResource;
  private WebResource.Builder builder;

  /**
   * Constructs an instance with the given HTTP method and {@link WebResource}.
   *
   * @param method      The HTTP method to use. It is preferred to use {@link HttpMethods} to guarantee correctness
   * @param webResource The {@link WebResource} representing the request
   */
  public SunJerseyHttpRequest(String method, WebResource webResource) {
    this.method = method;
    this.webResource = webResource;
    this.builder = webResource.getRequestBuilder();
  }

  @Override
  public void addHeader(String name, String value) throws IOException {
    builder = builder.header(name, value);
  }

  @Override
  public void setTimeout(int connectTimeout, int readTimeout) throws IOException {
    webResource.setProperty(ClientConfig.PROPERTY_CONNECT_TIMEOUT, connectTimeout);
    webResource.setProperty(ClientConfig.PROPERTY_READ_TIMEOUT, readTimeout);
  }

  @Override
  public LowLevelHttpResponse execute() throws IOException {
    if (null != getStreamingContent()) {
      if (!method.equals(HttpMethods.POST) && !method.equals(HttpMethods.PUT) && !method.equals(HttpMethods.PATCH)) {
        throw new IllegalStateException(String.format("Jersey client does not support '%s' requests with content.", method));
      }
      ByteArrayOutputStream entityOutputStream = new ByteArrayOutputStream();

      getStreamingContent().writeTo(entityOutputStream);
      builder = builder.entity(entityOutputStream.toString(), getContentType());
    }

    return new SunJerseyHttpResponse(builder.method(method, ClientResponse.class));
  }

}
