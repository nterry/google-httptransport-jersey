package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;

import java.io.IOException;

/**
 * Sub class of {@link HttpTransport} using {@link org.glassfish.jersey.client.JerseyClient} (Jersey 2.x) as a backend.
 *
 * @author Nicholas Terry
 */
public class GlassfishJerseyHttpTransport extends HttpTransport {

  @Override
  protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
    return null;
  }
}
