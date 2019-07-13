public class HPF implements Algorithm {
	public void run(Workload[] arrWorkload)
	{
		float totalWaitingTime=0,totalTurnAroundTime=0;
		int n = arrWorkload.length;
		int flag[] = new int[n];  // f means it is flag it checks process is completed or not
		
		System.out.println("\npid  arrival  brust  complete turn waiting priority");
		
		int systemTime=0, totalProcessExecuted=0;
 	//	boolean a = true;
 
		for(int i=0; i<n; i++)
		{
			flag[i] = 0;
		}
		
		while(true)
		{
			int current = n, min=999;
			if (totalProcessExecuted == n) // total no of process = completed process loop will be terminated
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

				currentWorkload.completionTime = systemTime + currentWorkload.executionTime;
				systemTime += currentWorkload.executionTime;

			    currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;          // turnaround time= completion time- arrival time
			    currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	// waiting time= turnaround time- burst time
			
				totalWaitingTime += currentWorkload.waitingTime ;              											 // total waiting time
				totalTurnAroundTime += currentWorkload.turnAroundTime ;  

				flag[current]=1;
				totalProcessExecuted++;
				System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.priority) ;
			}
		}
	
		// sc.close();
		System.out.println("\naverage waiting time: "+ (totalWaitingTime/n));     // printing average waiting time.
		System.out.println("average turnaround time: "+(totalTurnAroundTime/n));    // printing average turnaround time.
	}
}
