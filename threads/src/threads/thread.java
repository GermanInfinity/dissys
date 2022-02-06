package threads;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class thread {
    final static private int THREADS_NUM = 10000;
    private int count = 0; 
    
    synchronized public void increment() { 
    	count = count + 10; 
    }
    
    synchronized public void add_to_vector() { 
    	Vector<String> vec = new Vector<String>();
		for (int i = 0; i < 1000; i++) { 
			vec.add("thread" + i);
		}
    }

    synchronized public void add_to_arraylist() { 
    	ArrayList<String> arraylist = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) { 
			arraylist.add("thread" + i);
		}
    }
    
    synchronized public void add_to_hashtable() { 
    	Hashtable<Integer, String> ht1 = new Hashtable<>();
		for (int i = 0; i < 1000; i++) { 
			ht1.put(i, "added");
		}
    }
    
    synchronized public void add_to_hashmap() { 
    	HashMap<String, String> maps = new HashMap<String, String>();
		for (int i = 0; i < 1000; i++) { 
		    maps.put("This", "added");
		}
    }
    
    synchronized public void add_to_concurrenthashmap() { 
    	ConcurrentHashMap<Integer, String> m = new ConcurrentHashMap<>();
		for (int i = 0; i < 1000; i++) { 
		    m.put(i, "added");
		}
    }
    
    public int get_value() { 
    	return this.count;
    }
    
	public thread() {
		// TODO Auto-generated constructor stub
	}
	
	public static void counter_func() throws InterruptedException { 
		final thread counter = new thread();
		
		for (int i = 0; i < THREADS_NUM; i++) { 
			Runnable t = () -> { counter.increment(); };
			new Thread(t).start();
		}
		
		Thread.sleep(500);
		System.out.println("Value should be equal to " + THREADS_NUM + " It is: " + counter.get_value());
	}

	
	public static void collections_vector() throws InterruptedException { 
	    final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_vector(); };
		Thread var =  new Thread(t);
		var.start();
		var.join();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1; 
		
		System.out.println("Adding to vector on single thread takes:" + timeTaken + " ns");  
		
	}
	
	public static void collections_arraylist() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_arraylist(); };
		Thread var = new Thread(t);
		var.start();
		var.join();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		
		System.out.println("Adding to arraylist on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_hashtable() throws InterruptedException { 
		// create multiple threads with a for loop, and each loop each thread
		// runs the runnable 
		// time the whole for loop
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_hashtable(); };
		Thread var = new Thread(t);
		var.start();
		var.join();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  

		System.out.println("Adding to hashtable on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_hashmap() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_hashmap(); };
		Thread var = new Thread(t);
		var.start();
		var.join();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  

		System.out.println("Adding to hashmap on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_concurrent_hashmap() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_concurrenthashmap(); };
		Thread var = new Thread(t);
		var.start();
		var.join();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  

		System.out.println("Adding to concurrent hashmap on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// Step 1: Counter
		long time1 = System.nanoTime();
		counter_func();
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		System.out.println("Creating multple threads takes: " + timeTaken + " ns");  
		
		// Step 2: Collections
		collections_vector();
		collections_arraylist();
		collections_hashtable();
		collections_hashmap();
		collections_concurrent_hashmap();
		
//		String test = "Hello Ugo how-are you";
//		String[] out = test.split("-");
//		for (int i = 0; i < out.length; i++) { 
//			System.out.println(out[i]);
//		}
		//System.out.println(test);

	}

}
