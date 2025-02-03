package com.newsletter;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface NewsletterWorkflow {
    @WorkflowMethod
    void executeDailyNewsletter(String country, String category);
}
