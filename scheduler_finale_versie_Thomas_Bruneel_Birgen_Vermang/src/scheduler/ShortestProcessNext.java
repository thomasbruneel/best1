package scheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestProcessNext {

	public ArrayList<Proces> start(Queue<Proces> q) {
		Queue<Proces> queue = new LinkedList<Proces>();
		for (Proces p:q){
			queue.add(new Proces(p));
		}
		
		ArrayList<Proces>readyqueue = new ArrayList<Proces>();
		ArrayList<Proces>result=new ArrayList<Proces>();
		

		int time=0; //tijd die start als eerste proces aankomt
		
		//parameters per proces
		int endtime = 0;
		int starttime = 0;

		Proces p;

		time=queue.peek().getArrivaltime();
		while(!queue.isEmpty()||!readyqueue.isEmpty()){
			if(!queue.isEmpty()&&queue.peek().getArrivaltime()<=time){
				readyqueue.add(queue.remove());
			}
			if(!readyqueue.isEmpty()){
				p=readyqueue.remove(0);
		
				int arrivaltime=p.getArrivaltime();
				int servicetime=p.getServicetime();
		
				if(arrivaltime>=time){ // proces moet niet wachten en kan onmidddelijk naar processor
					starttime=p.getArrivaltime();
			
				}
				else{ // proces moet wel wachten
					starttime=time;
			    }
		
				p.setStarttime(starttime);
				endtime=starttime+servicetime;
				time=endtime;

				while(!queue.isEmpty()&&queue.peek().getArrivaltime()<=time){ // processen die aankomen terwijl een proces in de processor zit of juist uit de processor gaat, worden in de ready queue gestoken
						Proces x=queue.remove();
						readyqueue.add(x);
				}
			
				// readyqueue sorteren zodat proces met kleinste servicetime bovenaanzit
				readyqueue.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
				
				p.setEndtime(endtime);
				p.bereken();
				result.add(p);
			}
			// als er een tijdje geen processen in de ready queue zitten dan time verhogen naar de eerste volgende aankomend proces
			else{
				time=queue.peek().getArrivaltime();
			}
		
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
