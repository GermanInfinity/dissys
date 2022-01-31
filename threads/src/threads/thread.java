package threads;
import java.util.*;

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
		System.out.println(vec);
    }

    synchronized public void add_to_arraylist() { 
    	ArrayList<String> arraylist = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) { 
			arraylist.add("thread" + i);
		}
		System.out.println(arraylist);
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
		new Thread(t).start();
		
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		Thread.sleep(500);
		System.out.println("Adding to vector on single thread takes:" + timeTaken + " ns");  
		
	}
	
	public static void collections_arraylist() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_arraylist(); };
		new Thread(t).start();
		
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		Thread.sleep(500);
		System.out.println("Adding to arraylist on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_hashtable() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_arraylist(); };
		new Thread(t).start();
		
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		Thread.sleep(500);
		System.out.println("Adding to hashtable on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_hashmap() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_arraylist(); };
		new Thread(t).start();
		
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		Thread.sleep(500);
		System.out.println("Adding to hashmap on single thread takes: " + timeTaken + " ns");  
		
	}
	
	public static void collections_chashmap() throws InterruptedException { 
		final thread counter = new thread();
		
		long time1 = System.nanoTime();
		
		Runnable t = () -> { counter.add_to_arraylist(); };
		new Thread(t).start();
		
		long time2 = System.nanoTime();
		long timeTaken = time2 - time1;  
		Thread.sleep(500);
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
		
//		String test = "Hello Ugo how-are you";
//		String[] out = test.split("-");
//		for (int i = 0; i < out.length; i++) { 
//			System.out.println(out[i]);
//		}
		//System.out.println(test);

	}

}
