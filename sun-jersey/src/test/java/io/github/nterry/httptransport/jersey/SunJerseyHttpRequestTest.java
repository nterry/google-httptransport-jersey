package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.util.ByteArrayStreamingContent;
import com.google.api.client.util.StreamingContent;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SunJerseyHttpRequestTest {

  @Mock
  private WebResource webResource;

  @Mock
  private WebResource.Builder builder;

  private StreamingContent streamingContent;

  private LowLevelHttpRequest testModel;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    streamingContent = new ByteArrayStreamingContent(new byte[]{65, 66, 67, 68});

    when(webResource.getRequestBuilder()).thenReturn(builder);
    when(builder.header(anyString(), anyString())).thenReturn(builder);
    when(builder.entity(any(), anyString())).thenReturn(builder);
    when(builder.method(anyString(), eq(ClientResponse.class))).thenReturn(new ClientResponse(ClientResponse.Status.OK, null, null, null));

    testModel = new SunJerseyHttpRequest(HttpMethods.POST, webResource);
    testModel.setStreamingContent(streamingContent);
    testModel.setContentType("application/json");
  }

  @Test
  public void itShouldAddTheGivenHeaderToTheRequest() throws Exception {
    testModel.addHeader("foo", "bar");

    verify(builder).header("foo", "bar");
  }

  @Test
  public void itShouldSetTheTimeout() throws Exception {
    testModel.setTimeout(200, 400);

    verify(webResource).setProperty(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 200);
    verify(webResource).setProperty(ClientConfig.PROPERTY_READ_TIMEOUT, 400);
  }

  @Test
  public void itShouldSetTheEntity_WhenOneIsProvided() throws Exception {
    testModel.execute();

    verify(builder).entity("ABCD", "application/json");
  }

  @Test
  public void itShouldNotSetTheEntity_WhenOneIsNotProvided() throws Exception {
    testModel.setStreamingContent(null);
    testModel.execute();

    verify(builder, never()).entity(anyString(), anyString());
  }

  @Test(expectedExceptions = IllegalStateException.class)
  public void itShouldThrowAnException_WhenAnEntityIsProvided_AndTheHttpMethodDoesntSupportOne() throws Exception {
    testModel = new SunJerseyHttpRequest(HttpMethods.GET, webResource);
    testModel.setStreamingContent(streamingContent);
    testModel.setContentType("application/json");

    testModel.execute();
  }
}
