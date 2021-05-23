/* Daemon thread:
 * 1. low-priority thread
 * 2. JVM automatically terminates it when all helper threads have finished execution.
 * 3. provides services to user threads for tasks like i/o, garbage collection etc.
 */

class Daemon 
{
    public static void main(String [] args)
    {
        Thread t1 = new Thread(new NormalThread());
        Thread t2 = new Thread(new DaemonThread());
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }  
}

class NormalThread implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(7000);
            System.out.println("Inside normal thread.");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

/* automatically terminated by JVM when all worker threads are dead 
 * (used for i/0 and background services like bluetooth, nfc etc)
 */
class DaemonThread implements Runnable
{
    @Override
    public void run()
    {
        // demonstrates that daemon thread is only alive so long as any worker thread is alive
        while(true)
        {
            try
            {
                Thread.sleep(1000);
                System.out.println("Inside daemon thread.");
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}