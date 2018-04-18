package scheduler;

public class Proces {
	private int pid;
	private int arrivaltime;
	private int servicetime;
	
	private int waittime;
	private int starttime;
	private int endtime;
	private int tat;
	
	private double normtat;
	
	private int remaingservice; // de nog resterende service time
	private double R; // responsverhouding (hrrn)
	
	public Proces(){
		// default constructor
	}
	
	
	public Proces(int pid, int arrivaltime, int servicetime) {
		this.pid=pid;
		this.arrivaltime=arrivaltime;
		this.servicetime=servicetime;
		this.remaingservice=servicetime;
	}
	
	public Proces(Proces p) {
		this.pid=p.pid;
		this.arrivaltime=p.arrivaltime;
		this.servicetime=p.servicetime;
		this.waittime=0;
		this.starttime=0;
		this.endtime=0;
		this.remaingservice=p.servicetime;
		this.R=0;

	
	}
	
	public void verlaagRemaingService(int slice) {
		this.remaingservice=this.remaingservice-slice;
		
	}
	// parameters berekenen
	public void bereken() {
        this.tat = (endtime - arrivaltime);
        this.normtat = (double) this.tat / servicetime;
        this.waittime = endtime - arrivaltime - servicetime;
		
	}

	public int getPid() {
		return pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getArrivaltime() {
		return arrivaltime;
	}
	
	public void setArrivaltime(int arrivaltime) {
		this.arrivaltime = arrivaltime;
	}
	
	public int getServicetime() {
		return servicetime;
	}
	
	public void setServicetime(int servicetime) {
		this.servicetime = servicetime;
	}
	
	
	
	public int getWaittime() {
		return waittime;
	}


	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}


	public int getStarttime() {
		return starttime;
	}


	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}


	public int getEndtime() {
		return endtime;
	}


	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}
	
	

	public void setTat(int tat) {
		this.tat = tat;
	}
	
	public int getTat() {
		return tat;
	}


	public double getNormtat() {
		return normtat;
	}

	public void setNormtat(double normtat) {
		this.normtat = normtat;
	}
	
	public int getRemaingservice() {
		return remaingservice;
	}


	public void setRemaingservice(int remaingservice) {
		this.remaingservice = remaingservice;
	}
	
	public double getR() {
		return R;
	}


	public void setR(double r) {
		R = r;
	}
	

	public void print(){
		System.out.println("pid "+pid);
		System.out.println("arrival time "+arrivaltime);
		System.out.println("service time "+servicetime);
		System.out.println("startime "+starttime);
		System.out.println("endtime "+endtime);
		System.out.println("waittime "+waittime);
		System.out.println("TAT "+tat);
		System.out.println("normTAT "+normtat);
		System.out.println("R "+R);
		System.out.println("----------------");
		
		
	}
	
	public Proces(Proces p, boolean b) {
		this.pid=p.pid;
		this.arrivaltime=p.arrivaltime;
		this.servicetime=p.servicetime;
		this.waittime=p.waittime;
		this.starttime=p.starttime;
		this.endtime=p.endtime;
		this.tat=p.tat;
		this.normtat=p.normtat;
		this.remaingservice=p.remaingservice;
		this.R=p.R;

	}
	public Proces get(){return this;}




}
