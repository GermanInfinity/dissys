package io.swagger.client.model;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonObject;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;

public class ConcurrentClient {
    public static void main(String args[]) throws InterruptedException {
        // Getting command line arguements
        if (args.length < 5) {
            System.out.println("Input the appropriate amount of cmd line args.");
            return;
        }

        Integer c = 0;
        Integer numThreads = 0; // 64
        Integer numRuns = 0; // 8
        Integer numSkiers = 0; // 1024
        Integer numLifts = 0; // 40
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
        //String base_path = "http://ec2-52-90-170-195.compute-1.amazonaws.com:8080/server_war";
        String base_path = "http://localhost:8080/server_war_exploded";
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


        CountDownLatch countDownLatch1 = new CountDownLatch((int) (0.20 * launch));
        CountDownLatch countDownLatch2 = new CountDownLatch((int) (0.20 * numThreads));
        CountDownLatch countDownLatch3 = new CountDownLatch(0);
        Vector<Thread> all_threads = new Vector<Thread>();
        long start_phase = System.currentTimeMillis();

        /// PHASE 1
        for (int i = 0; i < launch; i++) {
            Integer start_time = 1;
            Integer end_time = 90;
            Integer skier_range_min = (numThreads * i) + 1;
            Integer skier_range_max = numThreads * (1 + i);
            Thread app1 = new Thread(new Phase("1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests));
            all_threads.add(app1);
            app1.start();
        }
        countDownLatch1.await();
        System.out.println("Phase 1 finish");
        /// PHASE 2
        for (int i = 0; i < numThreads; i++) {
            Integer start_time = 91;
            Integer end_time = 360;
            Integer skier_range_min = (numSkiers/numThreads * i) + 1;
            Integer skier_range_max = numSkiers/numThreads * (1 + i);
            Thread app2 = new Thread(new Phase("2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests));
            all_threads.add(app2);
            app2.start();

        }
        countDownLatch2.await();
        System.out.println("Phase 2 finish");
        /// PHASE 3
        for (int i = 0; i < 0.10 * launch; i++) {
            Integer start_time = 361;
            Integer end_time = 420;
            Integer skier_range_min = (numThreads * i) + 1;
            Integer skier_range_max = numThreads * (1 + i);
            Thread app3 = new Thread(new Phase("3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests));
            all_threads.add(app3);
            app3.start();
        }
        countDownLatch3.await();

        // Join all threads.
        for (int i = 0; i < all_threads.size(); i++) {
            all_threads.get(i).join();
        }

        long end_phase = System.currentTimeMillis();
        long run_time = end_phase - start_phase;
        System.out.println("Number of successful requests: " + successes);
        System.out.println("Number of unsuccessful requests: " + no_successes);
        System.out.println("Total run time of all phases: " + run_time);
        System.out.println("Total throughput: " + requests/run_time);
    }
}


class Phase implements Runnable {
    private String phase_num;
    private SkiersApi api;
    private CountDownLatch countDownLatch;
    private Double posts_to_send;
    private Integer skier_range_min;
    private Integer skier_range_max;
    private Integer liftID;
    private Integer time_start;
    private Integer time_end;
    private Integer successes;
    private Integer no_successes;
    private Integer requests;

    public Phase(String phase_num, SkiersApi api, CountDownLatch countDownLatch, Double posts_to_send, Integer skier_range_min, Integer skier_range_max, Integer liftID, Integer time_start, Integer time_end, Integer successes, Integer no_successes, Integer requests) {
        this.phase_num = phase_num;
        this.api = api;
        this.countDownLatch = countDownLatch;
        this.posts_to_send = posts_to_send;
        this.skier_range_min = skier_range_min;
        this.skier_range_max = skier_range_max;
        this.liftID = liftID;
        this.time_start = time_start;
        this.time_end = time_end;
        this.successes = successes;
        this.no_successes = no_successes;
        this.requests = requests;
    }

    public void run() {
        for (int i = 0; i < posts_to_send; i++) {
            try {
                // send post wait for 201, then send all requests
                // if response 4** or 5**, retry sending 5 times until deeming it failed
                Random ran = new Random();
                Integer random_skier_ID = ran.nextInt(skier_range_max) + skier_range_min;
                Integer random_time_range = ran.nextInt(time_end) + time_start;
                Integer random_lift_ID = ran.nextInt(liftID) + 5;
                Integer random_wait_time = ran.nextInt(10) + 0;
                LiftRide body = new LiftRide();
                body.setTime(random_time_range);
                body.setLiftID(random_lift_ID);
                body.setWaitTime(random_wait_time);

                long time_stamp = System.currentTimeMillis();
                System.out.println("Thread on phase  " + phase_num + " started: " + time_stamp);

                ApiResponse res = api.writeNewLiftRideWithHttpInfo(body, 45, "45", "45", random_skier_ID);
                requests += 1;
                Boolean success = false;
                if (res.getStatusCode() > 399 || res.getStatusCode() < 600) {
                    Integer num_tries = 5;
                    while (num_tries > 0) {
                        res = api.writeNewLiftRideWithHttpInfo(body, 45, "45", "45", random_skier_ID);
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
                } else {
                    successes += 1;
                }

                System.out.println("Thread on phase  " + phase_num + " ended: " + time_stamp);
                countDownLatch.countDown(); 

            } catch (ApiException e) {
                System.err.println("Exception from API.");
                e.printStackTrace();
            }
        }
    }
}
