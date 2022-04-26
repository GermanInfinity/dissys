
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {
    // Access rabbitMQ and consume message,
    // send to database.
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(64);
        ArrayList<Integer> durations = new ArrayList<Integer>();
        System.out.println("Start");
        for (int i = 1; i <= 10; i++)
        {
            // plain/[user_id_num]/conv1/[num]/conv2/[num]/fc3/[num]/fc4/[num]/softmax/[num]

            // Local
//            String path_to_post = "http://localhost:8080/server_war_exploded/plain/0/conv1/"+
//                                  "2"+"/conv2/"+"2"+"/fc3/"+"2"+"/fc4/"+"2"+"/softmax/"+"2";


            // EC2
            String path_to_post  = "http://ec2-52-2-76-100.compute-1.amazonaws.com:8080/server_war/plain/"+ "2" +
                               "/conv1/" + "4" + "/conv2/" + "4" + "/fc3/" + "4" + "/fc4/" + "4" + "/softmax/" + "4";

            Action task = new Action(path_to_post, "", durations);
            executor.execute(task);
        }
        executor.shutdown();
        System.out.println("Done");
    }
}


class Action implements Runnable {
    private String url;
    private String data;
    private ArrayList<Integer> durations;

    public Action(String url, String data, ArrayList<Integer> d) {
        this.url = url;
        this.data = data;
        this.durations = d;
    }
    public void run() {
        try {
            long start_time = System.currentTimeMillis();
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url))
                    .POST(HttpRequest.BodyPublishers.ofString(this.data))
                    .build();

            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.out.println(response.statusCode());
            long end_time = System.currentTimeMillis();
            long duration = end_time - start_time;

            this.durations.add((int)duration);
            System.out.println(this.durations);
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}