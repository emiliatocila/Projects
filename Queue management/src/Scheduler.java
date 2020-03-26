import java.util.ArrayList;

public class Scheduler {
	private ArrayList<Queue> queues;
	private int nrOfQueues;
	
	public Scheduler(int nrOfQueues) {
		this.nrOfQueues = nrOfQueues;
		queues = new ArrayList<Queue>(nrOfQueues);
		for(int i = 0; i < nrOfQueues; i++) {
			queues.add(new Queue());
			(new Thread(queues.get(i))).start();
		}
	}
	
	public void processClient(Client c) {
		Queue bestQueue = new Queue();
		int minWaitingTime = Integer.MAX_VALUE;
		for(Queue q : queues) {
			if(q.getServiceTime() < minWaitingTime) {
				minWaitingTime = q.getServiceTime();
				bestQueue = q;
			}
		}
		c.setTrueDoIncrementWaitingTime();
		bestQueue.addClient(c);
	}
	
	public ArrayList<Queue> getQueues(){
		return queues;
	}
}
