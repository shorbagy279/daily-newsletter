package com.newsletter;

import com.newsletter.model.Article;
import com.newsletter.model.Subscriber;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewsActivitiesImpl implements NewsActivities {
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    @Override
    public List<Article> fetchNewsArticles(String country, String category) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
            .scheme("https")
            .host("newsapi.org")
            .addPathSegment("v2")
            .addPathSegment("top-headlines")
            .addQueryParameter("country", country)
            .addQueryParameter("pageSize", "10")
            .addQueryParameter("apiKey", Config.get("newsapi.key"));

        if (category != null && !category.isEmpty()) {
            urlBuilder.addQueryParameter("category", category);
        }

        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("NewsAPI Error: " + response.code());
            }
            
           
            String responseBody = response.body().string();
            System.out.println("[DEBUG] NewsAPI Response: " + responseBody);
            
            JSONObject json = new JSONObject(responseBody);
            return parseArticles(json.getJSONArray("articles"), category);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch news articles", e);
        }
    }

    private List<Article> parseArticles(JSONArray articles, String category) {
        List<Article> result = new ArrayList<>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            result.add(new Article(
                category,
                item.optString("title", "No title"),
                item.optString("description", "No description"),
                item.optString("author"),
                item.optString("urlToImage"),
                item.optString("publishedAt", "Unknown date")
            ));
        }
        return result;
    }

    @Override
    public String formatNewsletterHtml(List<Article> articles) {
        String category = articles.isEmpty() ? "General" : articles.get(0).getCategory();
        
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>")
            .append(".article { margin-bottom: 20px; padding: 15px; border: 1px solid #e0e0e0; border-radius: 5px; }")
            .append("img { max-width: 300px; height: auto; }")
            .append("</style></head><body>")
            .append("<h1 style='color: #2c3e50;'>").append(category).append(" News Digest</h1>");

        if (articles.isEmpty()) {
            html.append("<p>No articles found for this category.</p>");
        } else {
            for (Article article : articles) {
                html.append("<div class='article'>")
                    .append("<h2 style='margin-top: 0;'>").append(escapeHtml(article.getTitle())).append("</h2>");
                
                if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
                    html.append("<img src='").append(escapeHtml(article.getUrlToImage())).append("' alt='Article image'>");
                }
                
                html.append("<p>").append(escapeHtml(article.getDescription())).append("</p>")
                    .append("<p style='color: #7f8c8d; font-size: 0.9em;'>")
                    .append("By ").append(escapeHtml(article.getAuthor() == null ? "Unknown" : article.getAuthor()))
                    .append(" &middot; ").append(article.getPublishedAt())
                    .append("</p></div>");
            }
        }
        
        return html.append("</body></html>").toString();
    }

    
    public void sendNewsletterToSubscribers(String category, String htmlContent) {
        
        List<Subscriber> subscribers = DatabaseHelper.getSubscribersByCategory(category);

       /*try {
            EmailSender.sendEmail(
                "a.elshorbagy@yodawy.com", // Hardcoded recipient
                "Your Daily News Digest"+subscribers.size(), 
                htmlContent
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }*/
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers found for category: " + category);
            return;
        }
        
        String subject = "Your " + category+ " Newsletter";
        for (Subscriber subscriber : subscribers) {
            try {
                EmailSender.sendEmail(subscriber.getEmail(), subject, htmlContent);
                System.out.println("Newsletter sent successfully to: " + subscriber.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to send email to " + subscriber.getEmail());
                e.printStackTrace();
            }
        }
    }

    /*@Override
    public void sendNewsletterEmail(String htmlContent) {
        try {
            EmailSender.sendEmail(
                "a.elshorbagy@yodawy.com", // Hardcoded recipient
                "Your Daily News Digest", 
                htmlContent
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }*/

    private String escapeHtml(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
    
    
}
