package scheduler;

import java.util.*;

public class Feedback{

	public ArrayList<Proces> start(Queue<Proces> q) {
		ArrayList<Proces> result = new ArrayList<>();
		final int NUMER_OF_QUEUES_MINUS_ONE = 2;
		Queue<fProces> dummyQueue = new LinkedList<>();
		List<Queue<fProces>> queues = new ArrayList<>();
    	for(int i = 0; i<=NUMER_OF_QUEUES_MINUS_ONE; i++){
    		queues.add(new LinkedList<>(dummyQueue));
		}

		//Eerste processen selecteren om het algoritme goed te doen verlopen
    	fProces processing = new fProces(q.remove());
		fProces nextInLine = new fProces(q.remove());
		fProces tempProces;
    	int time = processing.getArrivaltime();

    	while(!q.isEmpty() || !queuesLeeg(queues) || !(processing==null) || !(nextInLine==null)){

    		//We selecteren het volgende proces uit de input(indien er nog zijn)
    		if(nextInLine == null){
    			if(!q.isEmpty()) nextInLine = new fProces(q.remove());
			}

			//We selecteren welk proces de processor zal krijgen
			boolean isNull = nextInLine==null;

			if(!isNull && nextInLine.getArrivaltime()<=time){
				if(processing != null){
					queues.get(processing.raiseQueue(NUMER_OF_QUEUES_MINUS_ONE))
							.add(processing);
				}
    			processing = nextInLine;
    			nextInLine = null;
			}
			else{
				tempProces = getnextProcess(queues);
				if(tempProces != null){
					if(processing != null){
						queues.get(processing.raiseQueue(NUMER_OF_QUEUES_MINUS_ONE))
								.add(processing);
					}
					processing = tempProces;
				}
			}
			if(processing == null){
				time = nextInLine.getArrivaltime();
			}

			//We laten het proces uitvoeren
			else{
				if(processing.getStarttime() == 0) processing.setStarttime(time);
				int timeslice =(int)Math.pow(2, processing.getAtQueue())*16;

				if(timeslice < processing.getRemaingservice()){
					processing.setRemaingservice(processing.getRemaingservice()-timeslice);
					time += timeslice;
				}
				else{
					time += processing.getRemaingservice();
					processing.setEndtime(time);
					processing.bereken();
					result.add(processing.toProces());
					processing.setRemaingservice(0);
					processing = null;
				}
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
		double gemwaittime=(double) totWait/result.size();
		double gemTAT= (double) totTAT/result.size();
		double gemNormTAT=(double) totNormTAT/result.size();

		System.out.println("gemwaaittime :"+gemwaittime);
		System.out.println("gemTAT :"+gemTAT);
		System.out.println("gemnormtat :"+gemNormTAT);
		return result;
	}

	private fProces getnextProcess(List<Queue<fProces>> queues) {
		fProces nextProces = null;
		boolean bezet = false;

		for(int i=1; i<queues.size(); i++){
			if(!queues.get(i).isEmpty() && !bezet){
				nextProces = queues.get(i).remove();
				bezet = true;
			}
		}

		return  nextProces;
	}

	private boolean queuesLeeg(List<Queue<fProces>> queues) {
		boolean isLeeg = true;
		for(int i=0; i<queues.size(); i++){
			if(!queues.get(i).isEmpty()) isLeeg = false;
		}
		return isLeeg;
	}

	private class fProces extends Proces{
		private int atQueue;

		public fProces(){}

		public fProces(Proces p) {
			super(p);
			this.atQueue = 0;
		}

		public int raiseQueue(int max){
			if(atQueue < max) atQueue++;
			return atQueue;
		}

		public int getAtQueue(){return atQueue;}

		public Proces toProces(){
			return new Proces(get(), true);
		}
	}


}
