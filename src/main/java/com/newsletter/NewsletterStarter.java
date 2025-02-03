package com.newsletter;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class NewsletterStarter {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        
        startNewsletterWorkflow(client, "us", "sports");
        startNewsletterWorkflow(client, "us", "business");        
    }

    private static void startNewsletterWorkflow(WorkflowClient client, 
                                              String country, 
                                              String category) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
            .setTaskQueue("NEWSLETTER_QUEUE") 
            .setWorkflowId(country + "-" + category + "-newsletter")
            .build();

        NewsletterWorkflow workflow = client.newWorkflowStub(
            NewsletterWorkflow.class, 
            options
        );
        
        WorkflowClient.start(workflow::executeDailyNewsletter, country, category);
        System.out.println("Started workflow for " + country + "/" + category);
    }
}