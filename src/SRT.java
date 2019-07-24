public class SRT implements Algorithm {
	public void run(Workload[] arrWorkload) {
		float totalWaitingTime = 0,totalTurnAroundTime = 0, totalResponseTime = 0;
		int totalIdleTime = 0;
		int completedProcess = 0;
		int n = arrWorkload.length;
		int timeQuantam = 0;
		int MAX_BURST = 99;
		
		System.out.print("Shorted remaining time Simulation");
		System.out.println("\npid	at	et	st	ct	tat	wt	rt");
		
		//run until time quonta is less than max quonta for simulation or there is any ongoing process 
		//no new process will not start once quonta goes beyond max quoant
		for (timeQuantam = 0; completedProcess != n; timeQuantam++) {
			int minRemainingProcessIndex = -1;
			int processIndex = -1;
			int minRemaining = MAX_BURST;
			
			for(Workload currentWorkload : arrWorkload) {
				processIndex++;
				// if process is in ready queue and not completed 
				if(currentWorkload.arrivalTime <= timeQuantam &&
						(currentWorkload.executionTime - currentWorkload.runTime) > 0 ) {
					
					if (currentWorkload.executionTime - currentWorkload.runTime < minRemaining) {
						minRemaining = currentWorkload.executionTime - currentWorkload.runTime;
						minRemainingProcessIndex = processIndex;
					}
				}
			}
			
			// there is no process is in ready queue for execution.
			if (minRemainingProcessIndex == -1) {
				totalIdleTime++;
				continue;
			}
			
			
			// if this first time process getting executed 
			if (arrWorkload[minRemainingProcessIndex].runTime == 0) {
				arrWorkload[minRemainingProcessIndex].responseTime = timeQuantam - arrWorkload[minRemainingProcessIndex].arrivalTime;  
				arrWorkload[minRemainingProcessIndex].startTime = timeQuantam;
			}
					
			// Given one time quonta
			arrWorkload[minRemainingProcessIndex].runTime++;
							
			// checking if process complete
			if (arrWorkload[minRemainingProcessIndex].runTime == arrWorkload[minRemainingProcessIndex].executionTime) {
				completedProcess++;
						
				// Lets populate this process stats 
				arrWorkload[minRemainingProcessIndex].completionTime = timeQuantam+1;
				arrWorkload[minRemainingProcessIndex].turnAroundTime = arrWorkload[minRemainingProcessIndex].completionTime - arrWorkload[minRemainingProcessIndex].arrivalTime;          // turnaround time= completion time- arrival time
				arrWorkload[minRemainingProcessIndex].waitingTime = arrWorkload[minRemainingProcessIndex].turnAroundTime - arrWorkload[minRemainingProcessIndex].executionTime;          	// waiting time= turnaround time- burst time			

				// lets add it to cumulative stats 
				totalWaitingTime += arrWorkload[minRemainingProcessIndex].waitingTime;              											 // total waiting time
				totalTurnAroundTime += arrWorkload[minRemainingProcessIndex].turnAroundTime;
				totalResponseTime += arrWorkload[minRemainingProcessIndex].responseTime;
			}	
		}
			
		for(Workload currentWorkload : arrWorkload) {
			System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.startTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.runTime);
		}	
		
		System.out.println("\nTotal process executed to completion "+ completedProcess + " from " + n);
		System.out.println("\naverage waiting time: "+ (totalWaitingTime/completedProcess));     // printing average waiting time.
		System.out.println("\naverage turnaround time: "+(totalTurnAroundTime/completedProcess));    // printing average turnaround time.
		System.out.println("\naverage response time: "+ (totalResponseTime/completedProcess));
		System.out.println("\nTotal idle time: "+ totalIdleTime);
	}
}