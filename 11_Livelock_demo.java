import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Livelock_demo 
{
    public static void main(String[] args) 
    {
        DoWork2 w = new DoWork2();
        
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

class DoWork2
{
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public void work1()
    {
        while(true)
        {
            try
            {
                lock1.tryLock(1000, TimeUnit.MILLISECONDS);
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Acquired lock1 for work1.");

            if(lock2.tryLock())
            {
                System.out.println("Acquired lock2 for work1.");
            }
            else
            {
                lock1.unlock();
                System.out.println("Released lock2 for work1.");
                continue;
            }

            System.out.println("Completed work1.");
            break;
        }

        lock1.unlock();
        lock2.unlock();
    } 

    public void work2()
    {
        while(true)
        {
            try
            {
                lock2.tryLock(1000, TimeUnit.MILLISECONDS);
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Acquired lock2 for work2.");

            if(lock1.tryLock())
            {
                System.out.println("Acquired lock1 for work2.");
            }
            else
            {
                lock2.unlock();
                System.out.println("Released lock2 for work2.");
                continue;
            }

            System.out.println("Completed work2.");
            break;
        }

        lock2.unlock();
        lock1.unlock();
    }
}