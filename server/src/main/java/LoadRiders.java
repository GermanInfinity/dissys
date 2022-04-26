
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LoadRiders {
    // Access rabbitMQ and consume message,
    // send to database.
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(64);

        for (int i = 1; i <= 56; i++)
        {
            String skierID = String.valueOf(i);
            String liftID = String.valueOf(i*3);
            String path_to_post = "http://ec2-52-2-76-100.compute-1.amazonaws.com:8080/server_war/skiers/"+skierID+"/seasons/2019/days/1/skiers/"+liftID;
            Task task = new Task(path_to_post, "");
            executor.execute(task);
        }
        executor.shutdown();
    }
}


class Task implements Runnable {
    private String url;
    private String data;

    public Task(String url, String data) {
        this.url = url;
        this.data = data;
    }
    public void run() {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url))
                    .POST(HttpRequest.BodyPublishers.ofString(this.data))
                    .build();

            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.out.println(response.statusCode());
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}