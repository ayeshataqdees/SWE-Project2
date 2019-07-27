public class SRTF implements Algorithm {
	public void run(Workload[] arrWorkload) {
		float totalWaitingTime = 0,totalTurnAroundTime = 0, totalResponseTime = 0;
		int totalIdleTime = 0;
		int completedProcess = 0;
		int n = arrWorkload.length;
		int timeQuantam = 0;
		int MAX_BURST = 99;
		boolean isProcesstoExecuted = true;
		int MAX_QUONTA = 150;
		int lastFinishedProcessTime = 0;
		int idleTime = 0;
		
		System.out.println("Shorted remaining time Simulation" + n);
		System.out.println("\npid  Priority IdleTime arrival waitTime  startTime Execution complete turnAroundTime ResponseTime");
		
		StatsPerPriority statsPerPriority[] = new StatsPerPriority[4];
		for(int i = 0; i < 4; i++)
		{
			StatsPerPriority workload = new StatsPerPriority();
			statsPerPriority[i] = workload;
		}
		
		//run until time quonta is less than max quonta for simulation or there is any ongoing process 
		//no new process will not start once quonta goes beyond max quoant
		for (timeQuantam = 0; (timeQuantam < MAX_QUONTA || isProcesstoExecuted == true) && completedProcess != n; timeQuantam++) {
			int CP = -1;
			int processIndex = -1;
			int minRemaining = MAX_BURST;
				
			for(Workload currentWorkload : arrWorkload) {
				processIndex++;
				// if process is in ready queue and not completed 
				if(currentWorkload.arrivalTime <= timeQuantam &&
						(currentWorkload.executionTime - currentWorkload.runTime) > 0) {
					if (currentWorkload.runTime == 0 && timeQuantam >= MAX_QUONTA) {
						isProcesstoExecuted = false;
						break;
					}
					if (currentWorkload.executionTime - currentWorkload.runTime < minRemaining) {
						minRemaining = currentWorkload.executionTime - currentWorkload.runTime;
						CP = processIndex;
					}
				}
			}
			
			//no new process to start and also  no existing process in queue 
			if (isProcesstoExecuted == false && CP == -1) {
				break;
			}
			
			// there is no process in ready queue for execution.
			if (CP == -1) {
				idleTime++;
				continue;
			}
			
			
			// if this first time process getting executed 
			if (arrWorkload[CP].runTime == 0) {
				arrWorkload[CP].responseTime = timeQuantam - arrWorkload[CP].arrivalTime;  
				arrWorkload[CP].startTime = timeQuantam;
				arrWorkload[CP].idleTime = idleTime;
				idleTime = 0;
			}
					
			// Given one time quonta
			arrWorkload[CP].runTime++;
							
			// checking if process complete
			if (arrWorkload[CP].runTime == arrWorkload[CP].executionTime) {
				completedProcess++;
						
				// Lets populate this process stats 
				lastFinishedProcessTime = timeQuantam+1;
				arrWorkload[CP].completionTime = timeQuantam+1;
				arrWorkload[CP].turnAroundTime = arrWorkload[CP].completionTime - arrWorkload[CP].arrivalTime;          // turnaround time= completion time- arrival time
				arrWorkload[CP].waitingTime = arrWorkload[CP].turnAroundTime - arrWorkload[CP].executionTime;          	// waiting time= turnaround time- burst time			

				// lets add it to cumulative stats 
				totalWaitingTime += arrWorkload[CP].waitingTime;              											 // total waiting time
				totalTurnAroundTime += arrWorkload[CP].turnAroundTime;
				totalResponseTime += arrWorkload[CP].responseTime;
				totalIdleTime += arrWorkload[CP].idleTime;
				
				StatsPerPriority currentPerPrStats = statsPerPriority[arrWorkload[CP].priority - 1];
				
				currentPerPrStats.totalWaitingTime += arrWorkload[CP].waitingTime ;
				currentPerPrStats.totalTurnAroundTime += arrWorkload[CP].turnAroundTime;
				currentPerPrStats.totalResponseTime += arrWorkload[CP].responseTime;				
				currentPerPrStats.totalProcess += 1;	
				System.out.println(arrWorkload[CP].processId + "  \t " + arrWorkload[CP].priority + "\t" + arrWorkload[CP].idleTime + "\t" + arrWorkload[CP].arrivalTime + "\t" + arrWorkload[CP].waitingTime + "  \t "  + arrWorkload[CP].startTime + "  \t " + "\t" + arrWorkload[CP].executionTime  + "\t" + arrWorkload[CP].completionTime + "\t" + arrWorkload[CP].turnAroundTime +"\t"+"\t"+ arrWorkload[CP].responseTime) ;

			}	
		}
			
		System.out.println("\nTotal process executed to completion "+ completedProcess + " from " + n);
		System.out.println("\nAverage waiting time: "+ (totalWaitingTime/completedProcess));     // printing average waiting time.
		System.out.println("\nAverage turnaround time: "+(totalTurnAroundTime/completedProcess));    // printing average turnaround time.
		System.out.println("\nNo of process completed: " + completedProcess);
		System.out.println("\nAverage response time: "+ (totalResponseTime/completedProcess));
		System.out.println("\nThroughput: " +((float)completedProcess /(float)timeQuantam));
		System.out.println("\nTotal idle time: "+ totalIdleTime);
		
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