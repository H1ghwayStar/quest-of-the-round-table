using System;
using System.Threading;
using System.Threading.Tasks;

namespace PlayingWithTimers
{
	class TimerExample
	{

		static AutoResetEvent autoEvent = new AutoResetEvent(false);

		static StatusChecker statusChecker = new StatusChecker(10);
		static Timer stateTimer = new Timer(statusChecker.CheckStatus, autoEvent, 0, 1000);

		static void Main()
		{

			TimerExample.otherMethod ();

			Console.WriteLine ("blargh");
			Console.ReadLine ();
			Console.WriteLine ("bye");
		}

		public static void otherMethod(){
			Console.WriteLine ("This runs");
			Task.Factory.StartNew(() => TimerExample.printStuff());
			Task.Factory.StartNew (() => Console.ReadKey ()).Wait (TimeSpan.FromSeconds (4.9));
			Console.WriteLine ("Nefertiti.");
			stateTimer.Dispose ();
			Console.WriteLine ("\n Destroyed time in otherMethod()");

		}

		public static async void  printStuff(){
			

			// Create a timer that invokes CheckStatus immeadiately
			// and every 0.25 second thereafter.
			//Console.WriteLine("{0:h:mm:ss.fff} Creating timer.\n", 
				//DateTime.Now); No longer accurate since creating the time is done as a instance variable


			// When autoEvent signals, change the period to every half second.
			autoEvent.WaitOne();
			stateTimer.Change(0, 500);
			Console.WriteLine("\nChanging period to .5 seconds.\n");

			// When autoEvent signals the second time, dispose of the timer.
			autoEvent.WaitOne();
			await Task.Delay (1000); //give an extra grace second before destruction
			stateTimer.Dispose();
			Console.WriteLine("\nDestroying timer.");
		}
	}

	class StatusChecker
	{
		private int invokeCount;
		private int  maxCount;

		public StatusChecker(int count)
		{
			invokeCount  = 0;
			maxCount = count;
		}

		// This method is called by the timer delegate.
		public void CheckStatus(Object stateInfo)
		{
			AutoResetEvent autoEvent = (AutoResetEvent)stateInfo;
			Console.WriteLine("{0} Checking status {1,2}.", 
				DateTime.Now.ToString("h:mm:ss.fff"), 
				(++invokeCount).ToString());

			if(invokeCount == maxCount)
			{
				// Reset the counter and signal the waiting thread.
				invokeCount = 0;
				Console.WriteLine ("Trying to signal...");
				autoEvent.Set();
			}
		}
	}
}
