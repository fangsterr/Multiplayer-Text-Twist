package server;

public class Clock extends Thread
{
	private long savedTimestamp;
	private int timeLeft;
	private Server server;

	public Clock(Server s, int t)
	{
		server = s;
		//System.currentTimeMillis(); -- get back a long
		//Thread.sleep(500);
		timeLeft = t;
		savedTimestamp = System.currentTimeMillis();
		start();
	}

	public void run()
	{
		while(timeLeft >= 0)
		{
			long lapse = (long)(System.currentTimeMillis() - savedTimestamp);
			timeLeft -= (lapse/1000);
			server.setTime(timeLeft);
			try
			{
				Thread.sleep(600);
			}
			catch(java.lang.InterruptedException e)
			{
			}
		}
	}
}