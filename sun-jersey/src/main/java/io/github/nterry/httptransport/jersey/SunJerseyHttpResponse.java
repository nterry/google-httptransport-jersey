package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpResponse;
import com.sun.jersey.api.client.ClientResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Sub class of {@link HttpTransport} wrapping a {@link ClientResponse} (Jersey 1.x).
 *
 * @author Nicholas Terry
 */
public class SunJerseyHttpResponse extends LowLevelHttpResponse {

  private final ClientResponse clientResponse;

  /**
   * Creates a new instance with the given {@link ClientResponse}.
   *
   * @param clientResponse The {@link ClientResponse} representing the response from the server
   */
  public SunJerseyHttpResponse(ClientResponse clientResponse) {
    this.clientResponse = clientResponse;
  }

  @Override
  public InputStream getContent() throws IOException {
    return clientResponse.getEntityInputStream();
  }

  @Override
  public String getContentEncoding() throws IOException {
    return null;
  }

  @Override
  public long getContentLength() throws IOException {
    return clientResponse.getLength();
  }

  @Override
  public String getContentType() throws IOException {
    return clientResponse.getType().toString();
  }

  @Override
  public String getStatusLine() throws IOException {
    return String.format("http %d %s", getStatusCode(), getReasonPhrase());
  }

  @Override
  public int getStatusCode() throws IOException {
    return clientResponse.getStatus();
  }

  @Override
  public String getReasonPhrase() throws IOException {
    return clientResponse.getStatusInfo().getReasonPhrase();
  }

  @Override
  public int getHeaderCount() throws IOException {
    return clientResponse.getHeaders().size();
  }

  @Override
  public String getHeaderName(int index) throws IOException {
    return null;
  }

  @Override
  public String getHeaderValue(int index) throws IOException {
    return null;
  }
}
