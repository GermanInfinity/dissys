import java.util.concurrent.ConcurrentHashMap;

public class store_POJO {
    private static ConcurrentHashMap<String, String> messages_in_queue= new ConcurrentHashMap<String, String>();

    public void add_to_store(String message) {
        this.messages_in_queue.put(String.valueOf(java.time.LocalTime.now()), message);
    }

    public ConcurrentHashMap reveal() {
        return this.messages_in_queue;
    }
}
