package com.newsletter;

import com.newsletter.model.Article;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import java.util.List;

@ActivityInterface
public interface NewsActivities {
    @ActivityMethod
    List<Article> fetchNewsArticles(String country, String category);
    
    @ActivityMethod
    String formatNewsletterHtml(List<Article> articles);
    
    // @ActivityMethod
    // void sendNewsletterEmail(String htmlContent);

    @ActivityMethod
    void sendNewsletterToSubscribers(String category, String htmlContent);
}
