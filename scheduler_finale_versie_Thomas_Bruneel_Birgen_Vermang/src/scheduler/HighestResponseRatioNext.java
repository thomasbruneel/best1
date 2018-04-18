package scheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HighestResponseRatioNext {
	
	public ArrayList<Proces> start(Queue<Proces> q) {
		Queue<Proces> queue = new LinkedList<Proces>();
		for (Proces p:q){
			queue.add(new Proces(p));
		}
		ArrayList<Proces>readyqueue = new ArrayList<Proces>();
		ArrayList<Proces>result=new ArrayList<Proces>();
		
		int time=0; //tijd die start als eerste proces aankomt
		Proces p;


		while(!queue.isEmpty()||!readyqueue.isEmpty()){
			if(!queue.isEmpty()&&queue.peek().getArrivaltime()<=time){
					readyqueue.add(queue.remove());
				}
			if(!readyqueue.isEmpty()){
				p=readyqueue.remove(0);
				int servicetime=p.getServicetime();
				p.setStarttime(time);
				time=time+servicetime;
				p.setEndtime(time);
				p.bereken();
				
				// processen die aankomen terwijl een proces in de processor zit of juist uit de processor gaat, worden in de ready queue gestoken
				while(!queue.isEmpty()&&queue.peek().getArrivaltime()<=time){
					Proces x=queue.remove();
					readyqueue.add(x);
				}
				//van elk proces in de readyqueue zijn responseverhouding berekenen
				for(Proces proces:readyqueue){
					int w=0;
					double R=0;
					int s=0;
					s=proces.getServicetime();
					w=(time-proces.getArrivaltime());

					R=(double)(w+s)/s;
					proces.setR(R);
					
				}

				result.add(p);
				//readyqueue sorteren zodat proces met grootste responseverhouding vooraan in de rij zit
				readyqueue.sort((Proces p1,Proces p2)->Double.compare(p2.getR(), p1.getR()));
			
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
