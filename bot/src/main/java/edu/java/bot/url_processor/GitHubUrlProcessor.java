package edu.java.bot.url_processor;

public class GitHubUrlProcessor extends UrlProcessor{

    public GitHubUrlProcessor(UrlProcessor urlProcessor) {
        super(urlProcessor);
    }

    @Override
    protected String getValidHostName() {
        return "github.com";
    }
}
