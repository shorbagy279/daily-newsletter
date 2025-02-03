package com.newsletter;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class NewsletterWorker {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        
        Worker worker = factory.newWorker("NEWSLETTER_QUEUE"); 
        worker.registerWorkflowImplementationTypes(NewsletterWorkflowImpl.class);
        worker.registerActivitiesImplementations(new NewsActivitiesImpl());
        
        factory.start();
        System.out.println("Worker started for task queue: NEWSLETTER_QUEUE");
    }
}
