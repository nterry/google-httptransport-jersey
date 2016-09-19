package io.github.nterry.httptransport.jersey;

import com.google.api.client.http.LowLevelHttpResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.core.spi.component.ProviderFactory;
import com.sun.jersey.core.spi.component.ProviderServices;
import com.sun.jersey.core.spi.factory.InjectableProviderFactory;
import com.sun.jersey.core.spi.factory.MessageBodyFactory;
import com.sun.jersey.spi.MessageBodyWorkers;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.testng.Assert.*;

public class SunJerseyHttpResponseTest {

  private ClientResponse clientResponse;

  private LowLevelHttpResponse testModel;

  @BeforeMethod
  public void setUp() throws Exception {
    InBoundHeaders inBoundHeaders = new InBoundHeaders();
    inBoundHeaders.put("Content-Encoding", headerValues("UTF-8"));
    inBoundHeaders.put("Content-Length", headerValues("4"));
    inBoundHeaders.put("Content-Type", headerValues("application/json"));
    inBoundHeaders.put("X-Something", headerValues("woot", "pants"));

    ProviderFactory providerFactory = new ProviderFactory(new InjectableProviderFactory());
    ProviderServices providerServices = new ProviderServices(providerFactory, new HashSet<Class<?>>(), new HashSet<>());
    MessageBodyWorkers messageBodyWorkers = new MessageBodyFactory(providerServices, false);

    InputStream inputStream = new ByteArrayInputStream(new byte[] {65, 66, 67, 68});

    clientResponse = new ClientResponse(ClientResponse.Status.OK, inBoundHeaders, inputStream, messageBodyWorkers);

    testModel = new SunJerseyHttpResponse(clientResponse);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void itShouldThrowAnException_WhenTheClientResponseIsNull() throws Exception {
    new SunJerseyHttpResponse(null);
  }

  @Test
  public void itShouldReturnTheEntity() throws Exception {
    InputStream expected = new ByteArrayInputStream(new byte[] {65, 66, 67, 68});

    InputStream actual = testModel.getContent();

    Assert.assertEquals(IOUtils.toString(actual), IOUtils.toString(expected));
  }

  @Test
  public void itShouldReturnTheContentEncoding() throws Exception {
    String encoding = testModel.getContentEncoding();

    Assert.assertEquals(encoding, "UTF-8");
  }

  @Test
  public void itShouldGetTheContentLength() throws Exception {
    long length = testModel.getContentLength();

    Assert.assertEquals(length, 4);
  }

  @Test
  public void itShouldGetTheContentType() throws Exception {
    String type = testModel.getContentType();

    Assert.assertEquals(type, "application/json");
  }

  @Test
  public void itShouldGetTheStatusLine() throws Exception {
    String statusLine = testModel.getStatusLine();

    Assert.assertEquals(statusLine, "http 200 OK");
  }

  @Test
  public void itShouldGetTheStatusCode() throws Exception {
    int statusCode = testModel.getStatusCode();

    Assert.assertEquals(statusCode, 200);
  }

  @Test
  public void itShouldGetTHeReasonPhrase() throws Exception {
    String reasonPhrase = testModel.getReasonPhrase();

    Assert.assertEquals(reasonPhrase, "OK");
  }

  @Test
  public void itShouldGetTheNumberOfHeaders() throws Exception {
    int headersCount = testModel.getHeaderCount();

    Assert.assertEquals(headersCount, 4);
  }

  @Test
  public void itShouldGetTheNumberedHeaderKey() throws Exception {
    String key = testModel.getHeaderName(1);

    // The underlying implementation 'sorts' the keys...
    Assert.assertEquals(key, "X-Something");
  }

  @Test
  public void itShouldGetTheNumberedHeaderValue() throws Exception {
    String value = testModel.getHeaderValue(1);

    // The underlying implementation 'sorts' the keys thus affecting the values...
    Assert.assertEquals(value, "woot, pants");
  }


  private List<String> headerValues(String... headerValues) {
    List<String> headerValuesList = new ArrayList<>();
    headerValuesList.addAll(Arrays.asList(headerValues));

    return headerValuesList;
  }
}