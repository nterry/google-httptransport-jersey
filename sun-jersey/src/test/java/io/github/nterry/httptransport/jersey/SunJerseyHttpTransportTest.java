package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SunJerseyHttpTransportTest {

  @Mock
  private Client client;

  @Mock
  private WebResource webResource;

  private HttpTransport testModel;

  @BeforeMethod
  private void setUp() {
    MockitoAnnotations.initMocks(this);

    testModel = new SunJerseyHttpTransport(client);

    when(client.resource(anyString())).thenReturn(webResource);
  }

  @Test
  public void itShouldConstructTheAppropriateResource() throws Exception {
    HttpRequest httpRequest = testModel.createRequestFactory().buildGetRequest(new GenericUrl("http://www.google.com"));

    Assert.assertEquals(httpRequest.getRequestMethod(), HttpMethods.GET);
    Assert.assertEquals(httpRequest.getUrl(), new GenericUrl("http://www.google.com"));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void itShouldThrowAnException_WhenTheClientIsNull() {
    new SunJerseyHttpTransport(null);
  }
}
