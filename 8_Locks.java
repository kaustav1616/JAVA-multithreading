/* replaces synchronized block, but more flexible (unlock can be called from different function,
 * whereas in synchronized block, braces fix where lock and unlock occur)
 * (check difference with and w/o locking)
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Locks 
{
    private int counter = 0;
    private Lock lock = new ReentrantLock(true); // true / false for guarantee of fairness
    
    private void increment()
    {
        lock.lock();

        try
        {
            ++counter;
        }
        finally
        {
            lock.unlock();
        }
    }

    public int process()
    {
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment();
            }
        });
    
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment();
            }
        });
    
        t1.start();
        t2.start();

        try 
        {
            t1.join();
            t2.join();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }

        return counter;
    }
}

class Lock_Synch
{
    public static void main(String args[])
    {
        int counter, c = 0;

        for(int i = 0; i <= 99; ++i)
        {
            Locks l = new Locks();
            counter = l.process();

            if(counter != 200)
            {
                System.out.println("counter = " + counter);
                ++c;
            }
        }

        System.out.println("Number of times synchronization issues: " + c);
    }
}