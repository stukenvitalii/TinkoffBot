package edu.java.bot.url_processor;

public class StackOverflowUrlProcessor extends UrlProcessor {
    public StackOverflowUrlProcessor(UrlProcessor processor) {
        super(processor);
    }

    @Override
    protected String getValidHostName() {
        return "stackoverflow.com";
    }
}
