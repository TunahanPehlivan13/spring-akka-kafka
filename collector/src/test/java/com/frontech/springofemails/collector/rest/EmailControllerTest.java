package com.frontech.springofemails.collector.rest;

import com.frontech.springofemails.collector.Application;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class EmailControllerTest {

    private MockMvc mockMvc;

    private static ClientAndServer mockServer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldAddQueueWhenSendingDataset() throws Exception {

        serveDummyMailService(3, 2);
/*
        mockMvc.perform(post("/emails")
                .content(generateRequestXML(4, 2))
                .contentType(MediaType.APPLICATION_XML_VALUE)
        ).andExpect(status().is(200));*/
    }

    private void serveDummyMailService(int nrOfEmails, int nrOfUrls) {
        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/dummy-emails"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(generateRequestXML(nrOfEmails, nrOfUrls))
                                .withHeaders(
                                        new Header("Content-Type", "application/xml; charset=utf-8")
                                )
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

    private String generateRequestXML(int nrOfEmails, int nrOfUrls) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();

            Element dataset = document.createElement("dataset");
            document.appendChild(dataset);

            Element emails = document.createElement("emails");
            dataset.appendChild(emails);

            for (int counter = 0; counter < nrOfEmails; counter++) {

                Element email = document.createElement("email");
                email.appendChild(document.createTextNode("Sergey1"));
                emails.appendChild(email);
            }

            Element resources = document.createElement("resources");
            dataset.appendChild(resources);

            for (int counter = 0; counter < nrOfUrls; counter++) {

                Element url = document.createElement("url");
                url.appendChild(document.createTextNode("http://127.0.0.1:1080/dummy-emails"));
                resources.appendChild(url);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));

            return writer.getBuffer().toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        return "";
    }
}