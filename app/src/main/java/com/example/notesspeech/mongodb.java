package com.example.notesspeech;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class mongodb {


     public static class MongoClientConnectionExample {
        public void main(String[] args) {
            String connectionString = "mongodb+srv://malexandraneagu:<yzQwJ30m9DylPLnC>@cluster0.yymzn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();

            // Create a new client and connect to the server
            try (MongoClient mongoClient = MongoClients.create(settings)) {
                try {
                    // Send a ping to confirm a successful connection
                    MongoDatabase database = mongoClient.getDatabase("admin");
                    database.runCommand(new Document("ping", 1));
                    System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                } catch (MongoException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /*private static final String CONNECTION_STRING = "mongodb+srv://malexandraneagu:<yzQwJ30m9DylPLnC>@cluster0.yymzn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0/sample_mflix?ssl=true&authSource=admin";

    public static MongoClient getMongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }


    public static void insertDocument() {
        MongoClient mongoClient = mongodb.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("people");
        MongoCollection<Document> collection = database.getCollection("user");

        Document document = new Document("name", "John Doe")
                .append("email", "johndoe@example.com")
                .append("age", 30);

        collection.insertOne(document);


        mongoClient.close();
    }*/


}
