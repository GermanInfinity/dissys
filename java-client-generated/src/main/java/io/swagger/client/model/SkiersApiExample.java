package io.swagger.client.model;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;

public class SkiersApiExample {
    synchronized public void call_API(SkiersApi apiInstance, Integer resortID, String seasonID, String dayID, Integer skierID) {
        try {
            ApiResponse res = apiInstance.getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
            Integer verticalResult = apiInstance.getSkierDayVertical(resortID, seasonID, dayID, skierID);
        } catch (ApiException e) {
            System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Example for the GET
        // SkiersApi apiInstance = new SkiersApi();
        //String basePath = "http://localhost:8080/server_war_exploded";
        String basePath = "http://ec2-52-90-170-195.compute-1.amazonaws.com:8080/server_war";
        SkiersApi apiInstance = new SkiersApi();
        Integer resortID = 56; // Integer | ID of the resort
        String seasonID = "56"; // String | ID of the season
        String dayID = "56"; // String | ID of the day
        Integer skierID = 56; // Integer | ID of the skier
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath(basePath);

        // EXECUTE ONE REQUEST TO SERVER
        final SkiersApiExample action = new SkiersApiExample();
        long time1 = System.currentTimeMillis();
        Runnable t = () -> { action.call_API(apiInstance, resortID, seasonID, dayID, skierID); };
        Thread var =  new Thread(t);
        var.start();
        var.join();
        long time2 = System.currentTimeMillis();
        long timeTaken = time2 - time1;
        System.out.println("Time taken to execute one request to server " + timeTaken + "ms.");

        // EXECUTE 100 REQUESTS TO SERVER
        final SkiersApiExample action2 = new SkiersApiExample();
        long time_start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Runnable threadx = () -> { action2.call_API(apiInstance, resortID, seasonID, dayID, skierID); };
            Thread v =  new Thread(threadx);
            v.start();
            v.join();
        }
        long time_end = System.currentTimeMillis();
        long time_for_100 = time_end - time_start;
        System.out.println("Time taken to execute one hundred requests to server " + time_for_100 + "ms.");

        // CALCULATE LATENCY
        final SkiersApiExample action3 = new SkiersApiExample();
        //long time_start2 = System.currentTimeMillis();
        for (int i = 0; i < 32; i++) {
            Runnable threadx = () -> { action3.call_API(apiInstance, resortID, seasonID, dayID, skierID); };
            Thread v =  new Thread(threadx);
            long started = System.currentTimeMillis();
            System.out.println("Started: " + started);
            v.start();
            v.join();
            long ended = System.currentTimeMillis();
            System.out.println("Ended: " + ended);
        }
//        long time_end2 = System.currentTimeMillis();
//        long throughput_timing = time_end2 - time_start2;
//        System.out.println("Time taken to execute requests to server " + throughput_timing + "ms.");


    }
}
