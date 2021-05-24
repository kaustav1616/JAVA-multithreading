/* 2 separate threads try to acquire the same 2 locks in opposite order, leading to deadlock 
 * (in order to remove possibility of deadlock, make both threads acquire the 2 locks in the
 * same order, thus removing possibility of cyclic dependency in terms of resource requirement)
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Deadlock_demo 
{
    public static void main(String[] args) 
    {
        DoWork w = new DoWork();
        
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                w.work1();
            }    
        });

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                w.work2();
            }    
        });

        t1.start();
        t2.start();
    }
}

class DoWork
{
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public void work1()
    {
        lock1.lock();
        System.out.println("Acquired lock1 for work1.");

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        lock2.lock();
        System.out.println("Acquired lock2 for work1.");
        lock1.unlock();
        lock2.unlock();
        System.out.println("Completed work1.");
    } 

    public void work2()
    {
        lock2.lock();
        System.out.println("Acquired lock2 for work2.");

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        lock1.lock();
        System.out.println("Acquired lock1 for work2.");
        lock2.unlock();
        lock1.unlock();
        System.out.println("Completed work2.");
    }

    /* uncomment this section to see demonstration of deadlock removal
    public void work2()
    {
        lock1.lock();
        System.out.println("Acquired lock1 for work2.");

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        lock2.lock();
        System.out.println("Acquired lock2 for work2.");
        lock1.unlock();
        lock2.unlock();
        System.out.println("Completed work2.");
    }
     */
}