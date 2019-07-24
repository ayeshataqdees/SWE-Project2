

public class HPF_P implements Algorithm {
	public class StatsPerPriority {
	int totalWaitingTime;    		// Total Waiting Time
	int totalTurnAroundTime;     	// Total Turaround Time
	int totalResponseTime;     	    // Total Response Time
	int totalProcess;
}
	public void run(Workload[] arrWorkload)
	{
		float totalWaitingTime=0,totalTurnAroundTime=0,totalResponseTime=0;
		int n = arrWorkload.length;
		int flag[] = new int[n];  // f means it is flag it checks process is completed or not
		int remainingBurstTime[] = new int[n];  // This is temporary buffer to keep track of remaining execution time for each process
		
		System.out.println("\npid  arrival  brust  complete turn waiting response priority");
		
		int systemTime=0, totalProcessExecuted=0;
 	//	boolean a = true;
 
		for(int i=0; i<n; i++)
		{
			flag[i] = 0;
			remainingBurstTime[i] = arrWorkload[i].executionTime;
		}

		while(true)
		{
			int current = n, min=999;
			if (totalProcessExecuted == n || systemTime >= 150) // total no of process = completed process loop will be terminated
				break;
			
			for (int i=0; i<n; i++)
			{
				Workload tempWorkload = arrWorkload[i];
				/*
				 * If i'th process arrival time <= system time and its flag=0 and priority<min 
				 * That process will be executed first 
				 */ 
				if ((tempWorkload.arrivalTime <= systemTime) && (flag[i] == 0) && (tempWorkload.priority < min))
				{
					min = tempWorkload.priority;
					current = i;
				}
			}
			
			/* If current == n means current value can not updated because no process arrival time< system time so we increase the system time */
			if (current == n) 
				systemTime++;
			else
			{
				Workload currentWorkload = arrWorkload[current];

				if (remainingBurstTime[current] == currentWorkload.executionTime)
				{
					currentWorkload.responseTime = systemTime - currentWorkload.arrivalTime;
				}

				// Only one quantum is executed at a time
				currentWorkload.completionTime = systemTime + 1;
				systemTime += 1;
				remainingBurstTime[current] -= 1 ;

				// When remaining time is zero for current process, we can mark this process as completed and calculate the statistics
				if (remainingBurstTime[current] == 0)
				{
                   currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;          // turnaround time= completion time- arrival time
			       currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	// waiting time= turnaround time- burst time
			
				   totalWaitingTime += currentWorkload.waitingTime ;              											 // total waiting time
				   totalTurnAroundTime += currentWorkload.turnAroundTime ;
				   totalResponseTime +=  currentWorkload.responseTime;

				   flag[current]=1;
				   totalProcessExecuted++;
				   System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.responseTime + "\t" + currentWorkload.priority) ;
			    }
			}
		}
	
		// sc.close();

		System.out.println("\naverage waiting time: "+ (totalWaitingTime/totalProcessExecuted));     // printing average waiting time.
		System.out.println("average turnaround time: "+(totalTurnAroundTime/totalProcessExecuted));    // printing average turnaround time.
		System.out.println("average response time: "+(totalResponseTime/totalProcessExecuted));    // printing average response time.
		System.out.println("Total Process Completed: "+(totalProcessExecuted));    // printing Total completed Process.
	}
}
