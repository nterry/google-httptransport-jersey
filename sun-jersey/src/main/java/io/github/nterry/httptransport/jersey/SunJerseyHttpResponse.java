package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpResponse;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * @throws IllegalArgumentException if the {@link ClientResponse} is null
   */
  public SunJerseyHttpResponse(ClientResponse clientResponse) {
    this.clientResponse = validateClientResponse(clientResponse);
  }

  @Override
  public InputStream getContent() throws IOException {
    return clientResponse.getEntityInputStream();
  }

  @Override
  public String getContentEncoding() throws IOException {
    return getHeaders().get("Content-Encoding");
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
    return getHeaders().keySet().toArray(new String[]{})[index];
  }

  @Override
  public String getHeaderValue(int index) throws IOException {
    return getHeaders().values().toArray(new String[]{})[index];
  }


  private Map<String, String> getHeaders() {
    Map<String, String> headersMap = new HashMap<>();
    MultivaluedMap<String, String> headersMultiMap = clientResponse.getHeaders();

    for (Map.Entry<String, List<String>> headerEntry : headersMultiMap.entrySet()) {
      headersMap.put(headerEntry.getKey(), flattenHeaderValues(headerEntry.getValue()));
    }

    return headersMap;
  }

  private String flattenHeaderValues(List<String> headerValues) {
    StringBuilder stringBuilder = new StringBuilder();

    for (String header : headerValues) {
      stringBuilder.append(String.format("%s, ", header));
    }

    return stringBuilder.toString().substring(0, stringBuilder.length() - 2);
  }

  private static ClientResponse validateClientResponse(ClientResponse clientResponse) {
    if (null != clientResponse) {
      return clientResponse;
    }

    throw new IllegalArgumentException("Client response cannot be null!");
  }
}
