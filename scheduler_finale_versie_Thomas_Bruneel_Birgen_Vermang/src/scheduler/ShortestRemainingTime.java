package scheduler;

import java.util.ArrayList;
import java.util.Queue;

public class ShortestRemainingTime {

	public ArrayList<Proces>start(Queue<Proces> q) {
		int time=0;
		int next=0;
		int verschil;
		
		ArrayList<Proces> queue = new ArrayList<Proces>();
		ArrayList<Proces> readyqueue = new ArrayList<Proces>();
		ArrayList<Proces> result = new ArrayList<Proces>();
		for (Proces p:q){
			queue.add(new Proces(p));
		}
		Proces p;
		while(next<q.size()){
			if(readyqueue.isEmpty()){
				p=queue.get(next);
				readyqueue.add(p);
				time=p.getArrivaltime();
				next++;
			}
		        p=readyqueue.get(0);
                int pid = p.getPid()-1;
                // als proces voor de eerste keer in de processor gaat , zet starttime in
                if (queue.get(pid).getRemaingservice() == queue.get(pid).getServicetime()) { 
                    queue.get(pid).setStarttime(time);
                }
                
                verschil = queue.get(next).getArrivaltime() - time;
                
                // proces kan niet helemaal uitgevoerd worden tot volgend proces aankomt
                 if (queue.get(pid).getRemaingservice() > verschil) {
                    p.verlaagRemaingService(verschil);
                    readyqueue.add(queue.get(next));
                    time = queue.get(next).getArrivaltime();
                    next++;
                    // readyqueue sorteren zodat proces met kleinste servicetime voorraan staat
                    readyqueue.sort((Proces p1,Proces p2)->p1.getRemaingservice()-p2.getRemaingservice());
                }
                 
                 // proces kan uitgevoerd worden vooraleer het volgende proces aankomt
                else {                                                                      
                    time = time + readyqueue.get(0).getRemaingservice();
                    Proces pp=readyqueue.get(0);
                    pp.setEndtime(time);                         
                    pp.setRemaingservice(0);                     
                    pp.bereken();
                    result.add(pp);
                    readyqueue.remove(0);

               }

            }
		
            while(!readyqueue.isEmpty()){
            	Proces pp=readyqueue.get(0);
                pp.setStarttime(time);
                time = time + pp.getRemaingservice();
                pp.setEndtime(time);
                pp.bereken();
                result.add(pp);
                readyqueue.remove(0);

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
