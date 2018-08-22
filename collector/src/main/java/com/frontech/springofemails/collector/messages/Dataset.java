package com.frontech.springofemails.collector.messages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Dataset {

    @JacksonXmlElementWrapper(localName = "emails")
    private List<String> email = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "resources")
    private List<String> url = new ArrayList<>();

    public Dataset() {
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return getEmail().toString() + "-" + getUrl().toString();
    }
}
