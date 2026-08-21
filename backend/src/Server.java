import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
import java.sql.*;

public class Server {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(7070),0);

        server.createContext("/test", exchange -> {

            String response = "Server is running";

            exchange.sendResponseHeaders(200,response.length());

            exchange.getResponseBody().write(response.getBytes());

            exchange.close();

        });

   server.createContext("/login", exchange -> {

    if(exchange.getRequestMethod().equalsIgnoreCase("POST")){

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        StringBuilder body = new StringBuilder();
        String line;

        while((line = br.readLine()) != null){
            body.append(line);
        }

        String requestBody = body.toString();

        String email = requestBody.split("\"email\":\"")[1].split("\"")[0];
        String password = requestBody.split("\"password\":\"")[1].split("\"")[0];

        String response;

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT USER_ID FROM USERS WHERE EMAIL=? AND PASSWORD=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,email);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                response = "LOGIN_SUCCESS";
            }else{
                response = "INVALID_CREDENTIALS";
            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
            response = "SERVER_ERROR";
        }

        exchange.sendResponseHeaders(200,response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }

});
        server.createContext("/register", exchange -> {

    if(exchange.getRequestMethod().equalsIgnoreCase("POST")){

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        StringBuilder body = new StringBuilder();
        String line;

        while((line = br.readLine()) != null){
            body.append(line);
        }

        String requestBody = body.toString();
        System.out.println("Request Body: " + requestBody);

        String name = requestBody.split("\"name\":\"")[1].split("\"")[0];
        String email = requestBody.split("\"email\":\"")[1].split("\"")[0];
        String password = requestBody.split("\"password\":\"")[1].split("\"")[0];

        String response;

        try{

            Connection con = DBConnection.getConnection();

            String checkSql = "SELECT USER_ID FROM USERS WHERE EMAIL=?";
            PreparedStatement psCheck = con.prepareStatement(checkSql);

            psCheck.setString(1,email);

            ResultSet rs = psCheck.executeQuery();

            if(rs.next()){

                response = "EMAIL_EXISTS";

            }else{

                String insertSql = "INSERT INTO USERS VALUES (nextval('USER_SEQ'), ?, ?, ?)";

                PreparedStatement psInsert = con.prepareStatement(insertSql);

                psInsert.setString(1,name);
                psInsert.setString(2,email);
                psInsert.setString(3,password);

                psInsert.executeUpdate();

                response = "REGISTER_SUCCESS";

            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
            response = "SERVER_ERROR";
        }
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        exchange.sendResponseHeaders(200,response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }

});
server.createContext("/suggest", exchange -> {

    if(exchange.getRequestMethod().equalsIgnoreCase("GET")){

        String query = exchange.getRequestURI().getQuery();

        String emotion = query.split("&")[0].split("=")[1];
        String content = query.split("&")[1].split("=")[1];

        System.out.println("Emotion = "+emotion+" Content = "+content);

        String response = "";

        try{

            Connection con = DBConnection.getConnection();

            String sql =
            "SELECT s.title, s.link FROM SUGGESTIONS s " +
            "JOIN EMOTIONS e ON s.EMOTION_ID = e.EMOTION_ID " +
            "JOIN CONTENT_TYPES c ON s.CONTENT_ID = c.CONTENT_ID " +
            "WHERE LOWER(e.EMOTION_NAME)=? AND LOWER(c.CONTENT_NAME)=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, emotion.toLowerCase());
            ps.setString(2, content.toLowerCase());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                response += rs.getString("title") + "|" + rs.getString("link") + "\n";
            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
            response = "SERVER_ERROR";
        }

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

});

server.createContext("/history", exchange -> {

    if(exchange.getRequestMethod().equalsIgnoreCase("GET")){

        String response = "";

        try{

            Connection con = DBConnection.getConnection();

            String sql = """
                SELECT e.EMOTION_NAME,
                       c.CONTENT_NAME,
                       h.START_TIME,
                       h.END_TIME,
                       h.TIME_SPENT_MIN
                FROM USER_HISTORY h
                JOIN EMOTIONS e ON h.EMOTION_ID = e.EMOTION_ID
                JOIN CONTENT_TYPES c ON h.CONTENT_ID = c.CONTENT_ID
                ORDER BY h.HISTORY_ID DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                response += rs.getString("EMOTION_NAME") + "|" +
                            rs.getString("CONTENT_NAME") + "|" +
                            rs.getTimestamp("START_TIME") + "|" +
                            rs.getTimestamp("END_TIME") + "|" +
                            rs.getInt("TIME_SPENT_MIN") + "\n";

            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
            response = "SERVER_ERROR";
        }

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

        exchange.sendResponseHeaders(200,response.length());

        exchange.getResponseBody().write(response.getBytes());

        exchange.close();
    }
});
        server.start();

        System.out.println("Server started on port 7070");

    }
}