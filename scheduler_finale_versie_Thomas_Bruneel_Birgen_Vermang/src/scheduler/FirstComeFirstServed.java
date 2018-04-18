package scheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FirstComeFirstServed {

	public ArrayList<Proces> start(Queue<Proces> q) {
		ArrayList<Proces>result=new ArrayList<Proces>();
		Queue<Proces> queue = new LinkedList<Proces>();
		for (Proces p:q){
			queue.add(new Proces(p));
		}
		
		int time=queue.peek().getArrivaltime(); //tijd die start als eerste proces aankomt in ready queue
		
		int endtime;
		int starttime;
		
		Proces p;
		
		while(!queue.isEmpty()){
			p=queue.remove();
			int servicetime=p.getServicetime();
			int arrivaltime=p.getArrivaltime();
			
			if(time<=arrivaltime){ // proces moet niet wachten en kan onmidddelijk naar processor

				starttime=arrivaltime;

			}
			else{ // proces moet wel wachten
				starttime=time;
			}
			
			p.setStarttime(starttime);
			endtime=starttime+servicetime;
			p.setEndtime(endtime);
			time=endtime;
			p.bereken();
			result.add(p);

		}
		// gemiddelde parameters uitprinten
		int totWait = 0;
		int totTAT = 0;
		double totNormTAT = 0;
		result.sort((Proces p1,Proces p2)->(p1.getPid()-p2.getPid()));

		for (Proces x:result){
			totWait=totWait+x.getWaittime();
			totTAT=totTAT+x.getTat();
			totNormTAT=totNormTAT+x.getNormtat();
			
		}
		double gemwaittime=(double) totWait/q.size();
		double gemTAT= (double) totTAT/q.size();
		double gemNormTAT=(double) totNormTAT/q.size();

		System.out.println("gemwaaittime :"+gemwaittime);
		System.out.println("gemTAT :"+gemTAT);
		System.out.println("gemnormtat :"+gemNormTAT);


		return result;

	}
	

}
