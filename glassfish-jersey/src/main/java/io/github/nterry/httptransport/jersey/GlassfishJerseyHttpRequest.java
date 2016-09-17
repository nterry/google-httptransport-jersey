package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;

import java.io.IOException;

/**
 * Sub class of {@link LowLevelHttpRequest} using {@link org.glassfish.jersey.client.JerseyClient} (Jersey 2.x) as a backend.
 *
 * @author Nicholas Terry
 */
public class GlassfishJerseyHttpRequest extends LowLevelHttpRequest {

  @Override
  public void addHeader(String name, String value) throws IOException {

  }

  @Override
  public LowLevelHttpResponse execute() throws IOException {
    return null;
  }
}
