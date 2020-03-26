import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable{
	private Scheduler scheduler;
	private int simulationTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int minArrivalTime;
	private int maxArrivalTime;
	private int nrOfQueues;
	private int nrOfClients;
	private ArrayList<Client> clients;
	private boolean done = false;
	private View view;
	private String qEvol = "";
	private String info = "";
	private int currentTime = 0;
	private int peakHour = 0;
	private int maxClientsThisDayInAnHour = 0;
	private int nrOfClientsThisHour = 0;
	private int offPeakHour = 0;
	private int minClientsThisDayInAnHour = Integer.MAX_VALUE;
	private double averageServiceTime = 0;
	private double nrOfClientsThisDay = 0;
	private double averageWaitingTime = 0;

	public SimulationManager(int simulationTime, int minServiceTime, int maxServiceTime, int minArrivalTime, int maxArrivalTime, int nrOfQueues, int nrOfClients, View view) {
		this.simulationTime = simulationTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.nrOfQueues = nrOfQueues;
		this.nrOfClients = nrOfClients;
		clients = new ArrayList<Client>(nrOfClients);
		scheduler = new Scheduler(nrOfQueues);
		generateClients(nrOfClients);
		this.view = view;
	}

	public boolean isDone() {
		return done;
	}

	public void generateClients(int nrOfClients) {
		int arrivalTime = 0;
		int serviceTime = 0;
		for(int i = 0; i < nrOfClients; i++) {
			arrivalTime = (int)(Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;
			serviceTime = (int)(Math.random() * (maxServiceTime - minServiceTime + 1)) + minServiceTime;
			Client c = new Client(i, arrivalTime, serviceTime);
			clients.add(c);
		}
		Collections.sort(clients);
	}

	public void processQueues() {
		int j = 0;
		qEvol += "Current time: " + currentTime + "\n";
		if(!clients.isEmpty()) {
			while(clients.get(0).getArrivalTime() == currentTime) {
				scheduler.processClient(clients.get(0));
				averageServiceTime += clients.get(0).getServiceTime();
				nrOfClientsThisDay++;
				clients.remove(clients.get(0));
				if(clients.isEmpty())
					break;
			}
		}
		for(Queue q : scheduler.getQueues()) {
			q.incrementWaitingTimeForAllClients();
			if(currentTime == simulationTime - 1)
				averageWaitingTime += q.getWaitingTime();
			qEvol += j + ": " + q.toString() + "\n";
			j++;
		}
		qEvol += "\n";
		currentTime++;
	}

	public void run() {
		while(currentTime < simulationTime) {
			processQueues();
			try {
				nrOfClientsThisHour = 0;
				for(Queue q : scheduler.getQueues()) {
					nrOfClientsThisHour += q.getNrOfClientsInQueue();
				}
				sleep(1000);
				if(nrOfClientsThisHour > maxClientsThisDayInAnHour) {
					maxClientsThisDayInAnHour = nrOfClientsThisHour;
					peakHour = currentTime - 1;
				}
				if(nrOfClientsThisHour < minClientsThisDayInAnHour) {
					minClientsThisDayInAnHour = nrOfClientsThisHour;
					offPeakHour = currentTime - 1;
				}
				view.setQueues(qEvol);
				qEvol = "";
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(Queue q : scheduler.getQueues()) 
			q.setDone();
		averageServiceTime /= nrOfClientsThisDay;
		averageWaitingTime /= nrOfClientsThisDay;
		info += "Peak hour: " + peakHour + "\nOff peak hour: " + offPeakHour + "\nAverage service time: " + averageServiceTime + "\nAverage waiting time: " + averageWaitingTime;
		view.setInfo(info);
	}
}

