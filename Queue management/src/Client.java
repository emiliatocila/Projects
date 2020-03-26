
public class Client implements Comparable<Client>{
	private int ID;
	private int arrivalTime;
	private int serviceTime;
	private int waitingTime = 0;
	private boolean doIncrementWaitingTime = true;
	
	public Client(int ID, int arrivalTime, int serviceTime) {
		this.ID = ID;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}

	public int getID() {
		return ID;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public void decrementServiceTime() {
		serviceTime--;
	}
	
	public void setFalseDoIncrementWaitingTime() {
		doIncrementWaitingTime = false;
	}
	
	public void setTrueDoIncrementWaitingTime() {
		doIncrementWaitingTime = true;
	}
	
	public void incrementWaitingTime() {
		if(doIncrementWaitingTime == true)
			waitingTime++;
	}
	
	public int compareTo(Client c) {
		if(this.getArrivalTime() < c.getArrivalTime())
			return -1;
		else if(this.getArrivalTime() == c.getArrivalTime())
			return 0;
		else return 1;
	}
	
	public String toString() {
		String s = "";
		s += "Client " + ID + ", processing time: " + serviceTime + "; ";
		return s;
	}
}
