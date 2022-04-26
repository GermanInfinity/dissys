package io.swagger.client.model;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import java.io.File;
import java.io.FileWriter;
import com.google.gson.JsonObject;
import com.opencsv.CSVWriter;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;

public class CongestionClient {
    public static String get_phase_start_time(String phase, Vector<Thread> all_threads) {
        // get first phase 1, get last phase 1
        for (int i = 0; i < all_threads.size(); i++) {
            if (all_threads.get(i).getName().charAt(0) == phase.charAt(0)) {
                return all_threads.get(i).getName().substring(2);
            }
        }
        return "null";
    }

    public static String get_phase_end_time(String phase, Vector<Thread> all_threads) {
        for (int i = all_threads.size(); i-- >0; ) {
            if (all_threads.get(i).getName().charAt(0) == phase.charAt(0)) {
                return all_threads.get(i).getName().substring(2);
            }
        }
        return "null";
    }

    public static void main(String args[]) throws InterruptedException {
        // Getting command line arguements
        if (args.length < 5) {
            System.out.println("Input the appropriate amount of cmd line args.");
            return;
        }

        Integer c = 0; // 512 1024 80 3 8080
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
        File file = new File("/Users/Chioma_N/Desktop/Spring22/disSys/systems/utils/8-req.csv");

        // API Endpoints
        String base_path = "http://ec2-52-90-170-195.compute-1.amazonaws.com:8080/server_war";
        //String base_path = "http://localhost:8080/server_war_exploded";
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
        CountDownLatch countDownLatch3 = new CountDownLatch(1);
        Vector<Thread> all_threads = new Vector<Thread>();
        long start_phase = System.currentTimeMillis();
        System.out.println("Started threading.");
        // number of requests, phase number, duration
        Vector<Long> laten = new Vector<Long>();
        Vector<Long> laten2 = new Vector<Long>();
        Vector<Long> laten3 = new Vector<Long>();

        Vector<Long> received = new Vector<Long>();
        Vector<Long> sent = new Vector<Long>();
        Vector<Long> received2 = new Vector<Long>();
        Vector<Long> sent2 = new Vector<Long>();
        Vector<Long> received3 = new Vector<Long>();
        Vector<Long> sent3 = new Vector<Long>();

        /// PHASE 1
        for (int i = 0; i < launch; i++) {
            Integer start_time = 1;
            Integer end_time = 90;
            Integer skier_range_min = (numThreads * i) + 1;
            Integer skier_range_max = numThreads * (1 + i);
            long clock_time = System.currentTimeMillis();

            Thread app1 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app1);

            Thread app11 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app11);

            Thread app12 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app12);

            Thread app13 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app13);

            Thread app14 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app14);

            Thread app15 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app15);

            Thread app16 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app16);

            Thread app17 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app16);

            Thread app18 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app16);

            Thread app19 = new Thread(new Phasec(received, sent, laten, clock_time, "1", apiInstance, countDownLatch1, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "1 " + Long.toString(clock_time));
            all_threads.add(app16);

            app1.start();
            app11.start();
//            app12.start();
//            app13.start();
//            app14.start();
//            app15.start();
//            app16.start();
//            app17.start();
//            app18.start();
//            app19.start();
        }
        countDownLatch1.await();
        System.out.println("Phase 1 finish");
        //System.out.println("Phase 1:" + laten);
//        System.out.println("Phase 1 sent:" + sent);
//        System.out.println("Phase 1 received:" + received);


        /// PHASE 2
        for (int i = 0; i < numThreads; i++) {
            Integer start_time = 91;
            Integer end_time = 360;
            Integer skier_range_min = (numSkiers/numThreads * i) + 1;
            Integer skier_range_max = numSkiers/numThreads * (1 + i);
            long clock_time = System.currentTimeMillis();
            Thread app2 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app2);

            Thread app21 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app21);

            Thread app22 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app22);

            Thread app23 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app23);

            Thread app24 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app24);

            Thread app25 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app25);

            Thread app26 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app26);

            Thread app27 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app26);

            Thread app28 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app26);

            Thread app29 = new Thread(new Phasec(received2, sent2, laten2, clock_time, "2", apiInstance, countDownLatch2, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "2 " + Long.toString(clock_time));
            all_threads.add(app26);

            app2.start();
            app21.start();
//            app22.start();
//            app23.start();
//            app24.start();
//            app25.start();
//            app26.start();
//            app27.start();
//            app28.start();
//            app29.start();
        }
        countDownLatch2.await();
        System.out.println("Phase 2 finish");
        //System.out.println("Phase 2:" + laten2);
//        System.out.println("Phase 2 sent requests timestamp:" + sent2);
//        System.out.println("Phase 2 received requests timestamp:" + received2);


        /// PHASE 3
        for (int i = 0; i < 0.10 * launch; i++) {
            Integer start_time = 361;
            Integer end_time = 420;
            Integer skier_range_min = (numThreads * i) + 1;
            Integer skier_range_max = numThreads * (1 + i);
            long clock_time = System.currentTimeMillis();
            Thread app3 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app3);

            Thread app31 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app31);

            Thread app32 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app32);

            Thread app33 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app33);

            Thread app34 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app34);

            Thread app35 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app35);

            Thread app36 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app36);

            Thread app37 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app36);

            Thread app38 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app38);

            Thread app39 = new Thread(new Phasec(received3, sent3, laten3, clock_time, "3", apiInstance, countDownLatch3, posts_to_send_1, skier_range_min, skier_range_max, lifts, start_time, end_time, successes, no_successes, requests), "3 " + Long.toString(clock_time));
            all_threads.add(app39);

            app3.start();
            app31.start();
//            app32.start();
//            app33.start();
//            app34.start();
//            app35.start();
//            app36.start();
//            app37.start();
//            app38.start();
//            app39.start();
        }
        countDownLatch3.await();
        System.out.println("Phase 3 finish");
        //System.out.println("Phase 3:" + laten3);
        //System.out.println("Phase 3 sent:" + sent3);
        //System.out.println("Phase 3 received:" + received3);

        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = { "Phase", "Timestamp start", "Timestamp end" };
            writer.writeNext(header);

            for (int i = 0; i < sent.size(); i++) {
                String rec_time = "";
                String sent_time = Long.toString(sent.get(i));
                if (i >= received.size()){
                    rec_time = Long.toString(received.get(received.size()-1));
                }
                else {
                    rec_time = Long.toString(received.get(i));
                }
                String[] data1 = { "1", sent_time, rec_time};
                writer.writeNext(data1);
            }
            // 1, sent, received
            // 2, sent2, received2
            for (int i = 0; i < sent2.size(); i++) {
                String rec_time = "";
                String sent_time = Long.toString(sent2.get(i));
                if (i >= received2.size()){
                    rec_time = Long.toString(received2.get(received2.size()-1));
                }
                else {
                    rec_time = Long.toString(received2.get(i));
                }
                String[] data1 = { "2", sent_time, rec_time};
                writer.writeNext(data1);
            }
            // 3, sent3, received3
            for (int i = 0; i < sent3.size(); i++) {
                String rec_time = "";
                String sent_time = Long.toString(sent3.get(i));
                if (i >= received3.size()){
                    rec_time = Long.toString(received3.get(received3.size()-1));
                }
                else {
                    rec_time = Long.toString(received3.get(i));
                }
                String[] data1 = { "3", sent_time, rec_time};
                writer.writeNext(data1);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Join all threads.
        for (int i = 0; i < all_threads.size(); i++) {
            all_threads.get(i).join();
        }

        //long end_phase = System.currentTimeMillis();
        //long run_time = end_phase - start_phase;
//        System.out.println("Number of successful requests: " + successes);
//        System.out.println("Number of unsuccessful requests: " + no_successes);
//        System.out.println("Total run time of all phases: " + run_time);
//        System.out.println("Total throughput: " + requests/run_time);
//
//        // Get time difference for each phase
//        System.out.println("Start time of phase 1: " + get_phase_start_time("1", all_threads));
//        System.out.println("Start time of phase 2: " + get_phase_start_time("2", all_threads));
//        System.out.println("Start time of phase 3: " + get_phase_start_time("3", all_threads));
//        System.out.println("End time of phase 1: " + get_phase_end_time("1", all_threads));
//        System.out.println("End time of phase 2: " + get_phase_end_time("2", all_threads));
//        System.out.println("End time of phase 3: " + get_phase_end_time("3", all_threads));

    }
}


class Phasec implements Runnable {
    private Vector<Long> received;
    private Vector<Long> sent;
    private Vector<Long> data;
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
    private long start_time; // create list, make public, for each thread get start and stop of request
    // when finished, loop over to collect data

    public Phasec(Vector<Long> r, Vector<Long> times, Vector<Long> data, long start_time, String phase_num, SkiersApi api, CountDownLatch countDownLatch, Double posts_to_send, Integer skier_range_min, Integer skier_range_max, Integer liftID, Integer time_start, Integer time_end, Integer successes, Integer no_successes, Integer requests) {
        this.received = r;
        this.sent = times;
        this.data = data;
        this.start_time = start_time;
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
                sent.add(time_stamp);
                //System.out.println("Phase " + phase_num + " before sending post " + time_stamp);
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
                            long time_stamp_end = System.currentTimeMillis();
                            received.add(time_stamp_end);
                            //System.out.println("Phase " + phase_num + " after sending post " + time_stamp_end);
                            break;
                        }
                        num_tries -= 1;
                    }
                } else if (res.getStatusCode() == 200 || res.getStatusCode() == 201) {
                    success = true;
                    long time_stamp_end = System.currentTimeMillis();
                    received.add(time_stamp_end);
                    //System.out.println("Phase " + phase_num + " after sending post " + time_stamp_end);
                }
                if (!success) {
                    no_successes += 1;
                } else {
                    successes += 1;
                }
                long time_end = System.currentTimeMillis();
                long latenc = time_end - time_stamp;
                data.add(latenc);
                countDownLatch.countDown(); // release the latch

            } catch (ApiException e) {
                System.err.println("Exception from API.");
                e.printStackTrace();
            }
        }
    }

    public long get_start_time() {
        return this.start_time;
    }
    public String get_phase_num() {
        return this.phase_num;
    }
}
