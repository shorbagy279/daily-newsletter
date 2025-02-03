# Daily Newsletter

**Daily Newsletter** is a Java application that fetches news articles from NewsAPI, formats them into an HTML digest, and sends them via email using Temporal workflows.

## Features
- Fetches top news articles by country and category.
- Formats articles into a professional HTML newsletter.
- Sends emails to specified recipients.
- Implements Temporal workflows for reliable task management.

## Technologies
- **Java**: Core language.
- **Temporal**: Workflow orchestration.
- **Jakarta Mail**: Email sending.
- **NewsAPI**: Source for news articles.

## Setup
1. Clone the repository.
2. Build and run using Maven
   mvn clean install
   mvn compile exec:java -Dexec.mainClass=com.newsletter.NewsletterWorker
   mvn compile exec:java -Dexec.mainClass=com.newsletter.NewsletterStarter
