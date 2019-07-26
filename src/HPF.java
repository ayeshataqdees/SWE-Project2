public class HPF implements Algorithm {
	public void run(Workload[] arrWorkload)
	{
		float totalWaitingTime=0,totalTurnAroundTime=0,totalResponseTime=0,totalProcessExecuted=0;
		int lastProcessCompletionTime=0,totalIdleTime=0;
		int n = arrWorkload.length;
		int flag[] = new int[n];  // f means it is flag it checks process is completed or not
	
		StatsPerPriority statsPerPriority[] = new StatsPerPriority[4];
		for(int i = 0; i < 4; i++)
		{
			StatsPerPriority workload = new StatsPerPriority();
			statsPerPriority[i] = workload;
		}

		System.out.println("\nHPF:");
		System.out.println("\npid  Priority IdleTime arrival waitTime  Execution complete turnAroundTime ResponseTime");

		
		int systemTime=0 ;
 	//	boolean a = true;
 
		for(int i=0; i<n; i++)
		{
			flag[i] = 0;
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


				if(totalProcessExecuted == 0)
				{	
					currentWorkload.idleTime = currentWorkload.arrivalTime;
				}
				else
				{
					if(currentWorkload.arrivalTime > lastProcessCompletionTime)
					{
						currentWorkload.idleTime = currentWorkload.arrivalTime - lastProcessCompletionTime;
					}
					else
					{
						currentWorkload.idleTime = 0;
					}
				}

				currentWorkload.responseTime = systemTime - currentWorkload.arrivalTime;
				currentWorkload.completionTime = systemTime + currentWorkload.executionTime;
				systemTime += currentWorkload.executionTime;

			    currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;          // turnaround time= completion time- arrival time
			    currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	// waiting time= turnaround time- burst time
			
				totalWaitingTime += currentWorkload.waitingTime ;              											 // total waiting time
				totalTurnAroundTime += currentWorkload.turnAroundTime ;
				totalResponseTime += currentWorkload.responseTime;
				totalIdleTime += currentWorkload.idleTime;

				StatsPerPriority currentPerPrStats = statsPerPriority[currentWorkload.priority - 1];

				currentPerPrStats.totalWaitingTime += currentWorkload.waitingTime ;
				currentPerPrStats.totalTurnAroundTime += currentWorkload.turnAroundTime;
				currentPerPrStats.totalResponseTime += currentWorkload.responseTime;				
				currentPerPrStats.totalProcess += 1;				

				lastProcessCompletionTime = systemTime;
				flag[current]=1;
				totalProcessExecuted++;
				System.out.println(currentWorkload.processId + "  \t " + currentWorkload.priority + "\t" + currentWorkload.idleTime + "\t" + currentWorkload.arrivalTime + "\t" + currentWorkload.waitingTime + "  \t " + "\t" + currentWorkload.executionTime  + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t    " + currentWorkload.responseTime) ;
			}
		}
	
		// sc.close();
		System.out.println("\naverage waiting time: "+ (totalWaitingTime/totalProcessExecuted));     // printing average waiting time.
		System.out.println("average turnaround time: "+(totalTurnAroundTime/totalProcessExecuted));    // printing average turnaround time.
		System.out.println("average response time: "+(totalResponseTime/totalProcessExecuted));    // printing average response time.
		System.out.println("Total Process Completed: "+(totalProcessExecuted));    // printing Total completed Process.
		System.out.println("Throughput: " + (totalProcessExecuted/systemTime));
		System.out.println("total idle time: " + totalIdleTime);

		System.out.println("\nPriority Avg_WT Avg_TAT Avg_RT Total_Process");
		int i = 1;
		for(StatsPerPriority currentStats : statsPerPriority) 
		{	if (currentStats.totalProcess != 0)
			{
			    System.out.println("   " + i + "  \t " + (currentStats.totalWaitingTime/currentStats.totalProcess) + "\t" + (currentStats.totalTurnAroundTime/currentStats.totalProcess) + "\t" + (currentStats.totalResponseTime/currentStats.totalProcess) + "\t" + (currentStats.totalProcess)) ;
		    }
		    i++;
		}
	}
}
