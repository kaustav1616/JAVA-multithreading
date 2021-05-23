/* Interrupt is a cooperative mechanism (can politely ask to stop, cannot force)
 * (cannot force as forcefully interrupting thread may leave data in inconsistent state)
 */

public class Interrupt 
{
    public void process()
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    System.out.println("Inside thread");

                    if(Thread.currentThread().isInterrupted())
                    {
                        System.out.println("Thread interrupted.");
                        return; // better practice is to use Callable and throw InterruptedException
                    }
                }
            }
        });

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Interrupting thread.");
        t.interrupt(); // just sets a flag on thrad 't' (thread 't' can do what it wants with the interrupt)
    }    
}

class Interrupt_test
{
    public static void main(String args[])
    {
        Interrupt i = new Interrupt();
        i.process();
    }
}