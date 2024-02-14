package edu.java.bot.url_processor;

import java.net.URI;

public abstract class UrlProcessor {
    protected UrlProcessor nextProcessor;

    public UrlProcessor(UrlProcessor processor) {
        this.nextProcessor = processor;
    }

    protected abstract String getValidHostName();

    public final boolean isValidUrl(URI url) {
        if (getValidHostName().equals(url.getHost())) {
            return true;
        }
        if (this.nextProcessor != null) {
            return this.nextProcessor.isValidUrl(url);
        }
        return false;
    }
}
