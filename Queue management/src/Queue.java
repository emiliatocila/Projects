import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Thread.sleep;

public class Queue implements Runnable{
	private ArrayBlockingQueue<Client> queue;
	private ArrayList<Client> allClients = new ArrayList<Client>();
	private int queueCapacity = 100;
	private int serviceTime;
	private int waitingTime;
	private boolean done = false;
	
	public Queue() {
		queue = new ArrayBlockingQueue<Client>(queueCapacity);
	}
	
	public void addClient(Client c) {
		queue.add(c);
		allClients.add(c);
		this.serviceTime += c.getServiceTime();
	}

	public int getServiceTime() {
		return serviceTime;
	}
	
	public int getWaitingTime() {
		for(Client c : allClients) 
			waitingTime += c.getWaitingTime();	
		return waitingTime;
	}
	
	public int getNrOfClientsInQueue() {
		return queue.size();
	}
	
	public void setDone() {
		done = true;
	}
	
	public void incrementWaitingTimeForAllClients() {
		for(Client c : queue) 
			c.incrementWaitingTime();	
	}
	
	public void run() {
		while(done == false) {
			if(!queue.isEmpty()) {
				try {
					Client c = queue.element();
					c.setFalseDoIncrementWaitingTime();
					while(c.getServiceTime() > 0) {
						sleep(1000);
						c.decrementServiceTime();
					}
					queue.take();
				} catch(InterruptedException e) {
					e.getStackTrace();
				}
			}
		}
	}
	
	public String toString() {
		String s = "";
		for(Client c : queue)
			s += c.toString();
		return s;
	}
}
