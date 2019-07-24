
public class SJN implements Algorithm {
	public void run(Workload[] arrWorkload) {
		
		//float totalWaitingTime=0, totalTurnAroundTime=0;
		int n = arrWorkload.length;
		//int totalIdleTime = 0;
		//int completedProcess = 0;
		//int timeQuantam = 0;
		//boolean isProcessExecuted = false;
		//int MAX_QUONTA = 150;
		
		System.out.println("SJN");

		int st=0, tot=0;
		float avgwt=0, avgta=0;

		
		boolean a = true;
		while(true)
		{
			int c=n, min=999;
			if (tot == n) // total no of process = completed process loop will be terminated
				break;
			
			int i = 0;
			for(Workload currentWorkload : arrWorkload)
			{
				/*
				 * If i'th process arrival time <= system time and its flag=0 and burst<min 
				 * That process will be executed first 
				 */ 
				if ((currentWorkload.arrivalTime <= st) && (currentWorkload.flag == 0) && (currentWorkload.flag<min))
				{
					min=currentWorkload.executionTime;
					c = i;
				}
				i++;
			}
			
			/* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
			if (c==n) 
				st++;
			else
			{
			 arrWorkload[c].completionTime	=st + arrWorkload[c].executionTime ;
				st+= arrWorkload[c].executionTime;
				arrWorkload[c].turnAroundTime = arrWorkload[c].completionTime - arrWorkload[c].arrivalTime ;
				arrWorkload[c].waitingTime= arrWorkload[c].turnAroundTime - arrWorkload[c].executionTime;
				arrWorkload[c].flag=1;
				tot++;
			}
		}
		
		System.out.println("\npid  Arrival Execution Completion TATime WaitTime");
		
		
		for(Workload currentWorkload : arrWorkload)
		{
			avgwt+= currentWorkload.waitingTime;
			avgta+= currentWorkload.executionTime;
			System.out.println(currentWorkload.processId+"\t"+currentWorkload.arrivalTime+"\t"+currentWorkload.executionTime + "\t" +currentWorkload.completionTime+"\t"+currentWorkload.turnAroundTime+"\t"+currentWorkload.waitingTime);
		}
		
		
		System.out.println ("\nAverage TurnAroundTime is "+ (float)(avgta/n));
		System.out.println ("Average WeightingTime is "+ (float)(avgwt/n));
		//sc.close();
	}
}

