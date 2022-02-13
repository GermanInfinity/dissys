package io.swagger.client.model;
import java.util.Random;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;

public class Client {
    synchronized public void connect(SkiersApi api, Double posts_to_send, Integer skier_range_min, Integer skier_range_max, Integer liftID, Integer time_start, Integer time_end, Integer successes, Integer no_successes, Integer requests) {
        try {
            // send post wait for 201, then send all requests
            // if response 4** or 5**, retry sending 5 times until deeming it failed
            for (int i = 0; i < posts_to_send; i++) {
                Random ran = new Random();
                Integer random_skier_ID = ran.nextInt(skier_range_max) + skier_range_min;
                Integer random_lift_ID = ran.nextInt(liftID) + 5;
                ApiResponse res = api.writeNewLiftRideWithHttpInfo(null, null, null, null, random_skier_ID);
                requests += 1;
                Boolean success = false;

                if (res.getStatusCode() > 399 || res.getStatusCode() < 600) {
                    Integer num_tries = 5;
                    while (num_tries > 0) {
                        res = api.writeNewLiftRideWithHttpInfo(null, null, null, null, random_skier_ID);
                        requests += 1;
                        if (res.getStatusCode() == 200 || res.getStatusCode() == 201) {
                            success = true;
                            break;
                        }
                        num_tries -= 1;
                    }
                } else if (res.getStatusCode() == 200 || res.getStatusCode() == 201) {
                    success = true;
                }
                if (!success) {
                    no_successes += 1;
                }
                else {
                    successes += 1;
                }
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling SkiersApi#getSkierDayVertical");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
         if (args.length < 5) {
             return;
         }

         Integer c = 0;
         Integer numThreads = 0;
         Integer numRuns = 0;
         Integer numSkiers = 0;
         Integer numLifts = 0;
         for (String s : args) {
             if (c == 0) {
                 numThreads = Integer.parseInt(s);
             }
             else if (c == 1) {
                 numSkiers = Integer.parseInt(s);
             }
             else if (c == 2) {
                 numLifts = Integer.parseInt(s);
             }
             else if (c == 3) {
                 numRuns = Integer.parseInt(s);
             }
             else if (c == 4) {
                 Integer portAddress = Integer.parseInt(s);
             }
             c += 1;
         }

         // API Endpoints
         String base_path = "http://ec2-52-90-170-195.compute-1.amazonaws.com:8080/server_war";
         SkiersApi apiInstance = new SkiersApi();
         Integer resortID = 56; // Integer | ID of the resort
         String seasonID = "56"; // String | ID of the season
         String dayID = "56"; // String | ID of the day
         Integer skierID = 56; // Integer | ID of the skier
         ApiClient client = apiInstance.getApiClient();
         client.setBasePath(base_path);

         // Execute requests to server from client
         final Client action = new Client();
         Integer launch = numThreads / 4;
         Integer skier_num = numSkiers;
         Integer lifts = numLifts;
         Integer successes = 0;
         Integer no_successes = 0;
         Integer requests = 0;
         Double posts_to_send_1 = numRuns * 0.2 * (skier_num / launch);
         Double posts_to_send_2 = numRuns * 0.6 * (skier_num / launch);
         Double posts_to_send_3 = numRuns * 0.1;

         long start_phase = System.currentTimeMillis();
         for (int i = 0; i < launch; i++) {
             Integer time_range = 0;
             if (i > 0) {
                 Integer start_time = 1;
                 Integer end_time = 90;
                 Integer skier_range_min = (numThreads * i) + 1;
                 Integer skier_range_max = numThreads * (1 + i);

                 Runnable t = () -> { action.connect(apiInstance, posts_to_send_1, skier_range_min,skier_range_max,lifts,start_time,end_time,successes,no_successes, requests); };
                 Thread var =  new Thread(t);
                 var.start();
                 var.join();
             }
             else {
                 Integer start_time = 1;
                 Integer end_time = 90;
                 Integer starting_skier_min = 1;
                 Integer starting_skier_max = numThreads;

                 Runnable t = () -> { action.connect(apiInstance,posts_to_send_1,starting_skier_min,starting_skier_max,lifts,start_time,end_time,successes,no_successes, requests); };
                 Thread var =  new Thread(t);
                 var.start();
                 var.join();
             }

             if (i >= 0.20 * launch) {
                 for (int j = 0; j < numThreads; j++) {
                     if (j > 0) {
                         Integer start_time = 91;
                         Integer end_time = 360;
                         Integer skier_range_min = (numSkiers/numThreads * i) + 1;
                         Integer skier_range_max = numSkiers/numThreads * (1 + i);

                         Runnable t = () -> { action.connect(apiInstance,posts_to_send_2,skier_range_min,skier_range_max,lifts,start_time,end_time,successes,no_successes, requests); };
                         Thread var =  new Thread(t);
                         var.start();
                         var.join();
                     }
                     else {
                         Integer start_time = 91;
                         Integer end_time = 360;
                         Integer starting_skier_min = 1;
                         Integer starting_skier_max = numSkiers/numThreads;

                         Runnable t = () -> { action.connect(apiInstance,posts_to_send_2,starting_skier_min,starting_skier_max,lifts,start_time,end_time,successes,no_successes, requests); };
                         Thread var =  new Thread(t);
                         var.start();
                         var.join();
                     }

                     if (j >= 0.20 * numThreads) {
                         for (int k = 0; k < 0.10 * numThreads; k++) {
                             if (k > 0) {
                                 Integer start_time = 361;
                                 Integer end_time = 420;
                                 Integer skier_range_min = (numThreads * i) + 1;
                                 Integer skier_range_max = numThreads * (1 + i);

                                 Runnable t = () -> { action.connect(apiInstance,posts_to_send_3,skier_range_min,skier_range_max,lifts,start_time,end_time,successes,no_successes, requests); };
                                 Thread var =  new Thread(t);
                                 var.start();
                                 var.join();
                             }
                             else {
                                 Integer start_time = 361;
                                 Integer end_time = 420;
                                 Integer starting_skier_min = 1;
                                 Integer starting_skier_max = numThreads;

                                 Runnable t = () -> { action.connect(apiInstance,posts_to_send_3,starting_skier_min,starting_skier_max,lifts,start_time,end_time,successes,no_successes, requests); };
                                 Thread var =  new Thread(t);
                                 var.start();
                                 var.join();
                             }
                         }
                     }
                 }
             }
         }
         long end_phase = System.currentTimeMillis();
         long run_time = end_phase - start_phase;
         System.out.println("Number of successful requests: " + successes);
         System.out.println("Number of unsuccessful requests: " + no_successes);
         System.out.println("Total run time of all phases: " + run_time);
         System.out.println("Total throughput: " + requests/run_time);
    }
}
