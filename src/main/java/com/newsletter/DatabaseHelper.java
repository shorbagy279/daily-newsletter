package com.newsletter;

import com.newsletter.model.Subscriber; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/project1_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static List<Subscriber> getSubscribersByCategory(String category) {
        List<Subscriber> subscribers = new ArrayList<>();
        String sql = "SELECT name, email, interested_with,country FROM user_inputs WHERE interested_with = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Subscriber subscriber = new Subscriber();
                subscriber.setName(rs.getString("name"));
                subscriber.setEmail(rs.getString("email"));
                subscriber.setInterestedWith(rs.getString("interested_with"));
                subscriber.setCountry(rs.getString("country"));
                subscribers.add(subscriber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscribers;
    }
}
