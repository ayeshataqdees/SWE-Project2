public class FCFS implements Algorithm {
	public void run(Workload[] arrWorkload)
	{
		float totalWaitingTime=0, totalTurnAroundTime=0, totalIdleTime=0, completedProcess=0, totalResponseTime=0;
		float MAX_QUANTA = 150;
		int n = arrWorkload.length;
		boolean quantaReached = false;
		
		System.out.println("\nFCFS:");
		System.out.println("\npid  Priority IdleTime arrival waitTime  startTime Execution complete turnAroundTime ResponseTime");
		
		StatsPerPriority statsPerPriority[] = new StatsPerPriority[4];
		for(int i = 0; i < 4; i++)
		{
			StatsPerPriority workload = new StatsPerPriority();
			statsPerPriority[i] = workload;
		}
		
		int i = 0;
		
		
		for(Workload currentWorkload : arrWorkload)
		{
			// start finding completion times
			if(i == 0)
			{	
				currentWorkload.completionTime = currentWorkload.arrivalTime + currentWorkload.executionTime;
				currentWorkload.idleTime = currentWorkload.arrivalTime;
			}
			else
			{
				Workload previousWorkload = arrWorkload[i-1];
				
				if(previousWorkload.completionTime >= MAX_QUANTA || currentWorkload.arrivalTime > MAX_QUANTA) {
					quantaReached = true;
				}
				
				if(!quantaReached) {
					if(currentWorkload.arrivalTime > previousWorkload.completionTime)
					{
						currentWorkload.completionTime = currentWorkload.arrivalTime + currentWorkload.executionTime;
						currentWorkload.idleTime = currentWorkload.arrivalTime - previousWorkload.completionTime;
					}
					else
					{
						currentWorkload.completionTime = previousWorkload.completionTime + currentWorkload.executionTime;
						currentWorkload.idleTime = 0;
					}
				}
			}	
			
			if(!quantaReached) {
				currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;	      // turnaround time= completion time- arrival time
				currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;
				currentWorkload.startTime = currentWorkload.arrivalTime + currentWorkload.waitingTime;// waiting time= turnaround time- burst time
				currentWorkload.responseTime = currentWorkload.startTime - currentWorkload.arrivalTime;
				
				if(currentWorkload.startTime <= MAX_QUANTA) {
					completedProcess++;
				}
				
				
				totalWaitingTime += currentWorkload.waitingTime ;              											 // total waiting time
				totalTurnAroundTime += currentWorkload.turnAroundTime;          									// total turnaround time
				totalIdleTime += currentWorkload.idleTime;
				totalResponseTime += currentWorkload.responseTime;
				
				StatsPerPriority currentPerPrStats = statsPerPriority[currentWorkload.priority - 1];
				
				currentPerPrStats.totalWaitingTime += currentWorkload.waitingTime ;
				currentPerPrStats.totalTurnAroundTime += currentWorkload.turnAroundTime;
				currentPerPrStats.totalResponseTime += currentWorkload.responseTime;				
				currentPerPrStats.totalProcess += 1;	
			}
			System.out.println(currentWorkload.processId + "  \t " + currentWorkload.priority + "\t" + currentWorkload.idleTime + "\t" + currentWorkload.arrivalTime + "\t" + currentWorkload.waitingTime + "  \t "  + currentWorkload.startTime + "  \t " + "\t" + currentWorkload.executionTime  + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime +"\t"+ currentWorkload.responseTime) ;

			// end finding completion times
			
			i++;
		}
		
	
		// sc.close();
		System.out.println("\nAverage waiting time: "+ (totalWaitingTime/n));     // printing average waiting time.
		System.out.println("Average turnaround time: "+(totalTurnAroundTime/n));    // printing average turnaround time.
		System.out.println("No. of process completed: " + completedProcess);
		System.out.println("Throughput: " + completedProcess/MAX_QUANTA);
		System.out.println("Total idle time: " + totalIdleTime);
		System.out.println("Average Response Time: "+ (totalResponseTime/n));
		
		System.out.println("\nPriority Avg_WT Avg_TAT Avg_RT Total_Process");
		int x = 1;
		for(StatsPerPriority currentStats : statsPerPriority) 
		{	if (currentStats.totalProcess != 0)
			{
			    System.out.println("   " + x + "  \t " + (currentStats.totalWaitingTime/currentStats.totalProcess) + "\t" + (currentStats.totalTurnAroundTime/currentStats.totalProcess) + "\t" + (currentStats.totalResponseTime/currentStats.totalProcess) + "\t" + (currentStats.totalProcess)) ;
		    }
		    x++;
		}
	
	}
}

