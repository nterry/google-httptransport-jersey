package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;

import java.io.IOException;

/**
 * Sub class of {@link LowLevelHttpRequest} using {@link com.sun.jersey.api.client.Client} (Jersey 1.x) as a backend.
 *
 * @author Nicholas Terry
 */
public class SunJerseyHttpRequest extends LowLevelHttpRequest {

  @Override
  public void addHeader(String name, String value) throws IOException {

  }

  @Override
  public LowLevelHttpResponse execute() throws IOException {
    return null;
  }
}
