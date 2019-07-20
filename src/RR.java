
public class RR implements Algorithm {
	public void run(Workload[] arrWorkload) 
	{
	float totalWaitingTime = 0,
	totalTurnAroundTime = 0, 
	totalResponseTime = 0;
	
	int totalIdleTime = 0;
	int completedProcess = 0;
	int n = arrWorkload.length;
	int timeQuantam = 0;
	boolean isProcessExecuted = false;
	int MAX_QUONTA = 150;

	System.out.print("Round Robin Simulation");
	System.out.println("\npid     at     bt      st		ct     tat       wt	gt");
	//run until time quonta is less than max quonta for simulation or there is any ongoing process 
	//no new process will not start once quonta goes beyond max quoant
	while (timeQuantam < MAX_QUONTA || isProcessExecuted == true)
	{
	isProcessExecuted = false;
	for(Workload currentWorkload : arrWorkload) 
		{
		// if process is in ready queue
		if(currentWorkload.arrivalTime <= timeQuantam &&
		currentWorkload.giventime < currentWorkload.executionTime) 
		{
		// check we are not starting new process beyond quonta limit 
		if (currentWorkload.giventime == 0 && timeQuantam >= MAX_QUONTA)
			{
			continue;
			}
		// if this first time process getting executed 
		if (currentWorkload.giventime == 0) 
			{
			currentWorkload.responsetime = timeQuantam - currentWorkload.arrivalTime;  
			currentWorkload.startTime = timeQuantam;
			}
			// Given one time quonta
			currentWorkload.giventime++;
			timeQuantam++;
			//set that this iteration cpu got some process to execute
			isProcessExecuted = true;
			// checking if process complete
		if (currentWorkload.giventime == currentWorkload.executionTime) 
			{
			// since not all process will complete, count the process which we 
			// are completing so that we can use this for avg calculation.
			completedProcess++;
			// Lets populate this process stats 
			currentWorkload.completionTime = timeQuantam;
			// turnaround time= completion time- arrival time
			currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;         
			// waiting time= turnaround time- burst time
			currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	
			// lets add it to cumulative stats 
			totalWaitingTime += currentWorkload.waitingTime;              											 // total waiting time
			totalTurnAroundTime += currentWorkload.turnAroundTime;
			totalResponseTime += currentWorkload.responsetime;
			// add total response
			// System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.giventime);

			}

		}	

		}
	if (isProcessExecuted == false) 
	{
	timeQuantam++;
	totalIdleTime++;
	}	
	}
	for(Workload currentWorkload : arrWorkload)
	{
		System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.startTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.giventime);
	}	
	// sc.close();
	System.out.println("\nTotal process executed to completion "+ completedProcess + " from " + n);
	System.out.println("\naverage waiting time: "+ (totalWaitingTime/completedProcess));     // printing average waiting time.
	System.out.println("\naverage turnaround time: "+(totalTurnAroundTime/completedProcess));    // printing average turnaround time.
	System.out.println("\naverage response time: "+ (totalResponseTime/completedProcess));
	System.out.println("\nTotal idle time: "+ totalIdleTime);

}
}	
}
}
