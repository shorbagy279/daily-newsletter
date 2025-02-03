package com.newsletter;

import com.newsletter.model.Article;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import java.util.List;

public class NewsletterWorkflowImpl implements NewsletterWorkflow {

    private final NewsActivities activities = Workflow.newActivityStub(
        NewsActivities.class,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(10))
            .setRetryOptions(RetryOptions.newBuilder()
                .setInitialInterval(Duration.ofSeconds(1))
                .setMaximumAttempts(3)
                .build())
            .build()
    );

    @Override
    public void executeDailyNewsletter(String country, String category) {
        try {
            
            List<Article> articles = activities.fetchNewsArticles(country, category);
            String htmlContent = activities.formatNewsletterHtml(articles);
            
            Workflow.getLogger(this.getClass()).info("Fetched {} articles", articles.size());
            
            activities.sendNewsletterToSubscribers(category, htmlContent);
            
            Workflow.getLogger(this.getClass())
                .info("Successfully sent newsletter for {}/{}", country, category);
        } catch (Exception e) {
            Workflow.getLogger(this.getClass())
                .error("Workflow failed for {}/{}: {}", country, category, e.getMessage());
            throw e;
        }
    }
}
