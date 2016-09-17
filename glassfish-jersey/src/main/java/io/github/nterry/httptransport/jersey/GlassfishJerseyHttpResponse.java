package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.LowLevelHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Sub class of {@link LowLevelHttpResponse} wrapping a {@link org.glassfish.jersey.client.ClientResponse} (Jersey 2.x).
 *
 * @author Nicholas Terry
 */
public class GlassfishJerseyHttpResponse extends LowLevelHttpResponse {

  @Override
  public InputStream getContent() throws IOException {
    return null;
  }

  @Override
  public String getContentEncoding() throws IOException {
    return null;
  }

  @Override
  public long getContentLength() throws IOException {
    return 0;
  }

  @Override
  public String getContentType() throws IOException {
    return null;
  }

  @Override
  public String getStatusLine() throws IOException {
    return null;
  }

  @Override
  public int getStatusCode() throws IOException {
    return 0;
  }

  @Override
  public String getReasonPhrase() throws IOException {
    return null;
  }

  @Override
  public int getHeaderCount() throws IOException {
    return 0;
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
