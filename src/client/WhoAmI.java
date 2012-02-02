package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WhoAmI
{
/**
* Tells you the domain name and IP of the machine
* you are running.
*
* @param args not used.
*/
	public static void main (String[] args)
	{
		try
		{
		 InetAddress localaddr = InetAddress.getLocalHost();
		 System.out.println( "main Local IP Address : " + localaddr.getHostAddress() );
		 System.out.println( "main Local hostname   : " + localaddr.getHostName() );
		 System.out.println();

		 InetAddress[] localaddrs = InetAddress.getAllByName ( "localhost" );
		 for ( int i=0; i<localaddrs.length; i++ )
		 {
			 if ( ! localaddrs[i].equals( localaddr ) )
			 {
				 System.out.println( "alt  Local IP Address : " + localaddrs[i].getHostAddress() );
				 System.out.println( "alt  Local hostname   : " + localaddrs[i].getHostName() );
				 System.out.println();
			}
		}
	 }
	catch ( UnknownHostException e )
	 {
	 System.err.println( "Can't detect localhost : " + e );
	 }
	}
}