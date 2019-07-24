public class FCFS implements Algorithm {
	public void run(Workload[] arrWorkload)
	{
		float totalWaitingTime=0,totalTurnAroundTime=0;
		int n = arrWorkload.length;
		
		System.out.println("\nFCFS:");
		System.out.println("\npid  arrival  brust  complete turn waiting priority");
		
		for(Workload currentWorkload : arrWorkload)
		{
			// finding completion times
			int i = 0;
			if(i == 0)
			{	
				currentWorkload.completionTime = currentWorkload.arrivalTime + currentWorkload.executionTime;
			}
			else
			{
				Workload previousWorkload = arrWorkload[i-1];

				if(currentWorkload.arrivalTime > previousWorkload.completionTime)
				{
					currentWorkload.completionTime = currentWorkload.arrivalTime + currentWorkload.executionTime;
				}
				else
					currentWorkload.completionTime = previousWorkload.completionTime + currentWorkload.executionTime;
			}
			
			
			currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;          // turnaround time= completion time- arrival time
			currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	// waiting time= turnaround time- burst time
			
			totalWaitingTime += currentWorkload.waitingTime ;              											 // total waiting time
			totalTurnAroundTime += currentWorkload.turnAroundTime ;               									// total turnaround time
			
			System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.priority) ;
			i++;
		}

	
		// sc.close();
		System.out.println("\naverage waiting time: "+ (totalWaitingTime/n));     // printing average waiting time.
		System.out.println("average turnaround time: "+(totalTurnAroundTime/n));    // printing average turnaround time.
	}
}