package com.newsletter;

public class EmailTest {
    public static void main(String[] args) {
        String recipient = "abdulrhmanm557@gmail.com"; 
        String subject = "Test Email";
        String testHtml = "<h1>Test Email</h1><p>This is a test email</p>";
        
        try {
            EmailSender.sendEmail(recipient, subject, testHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}