import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Synch_locks 
{
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce()
    {
        lock.lock();

        try
        {
            System.out.println("Starting producer.");
            condition.await();
            System.out.println("Ending producer.");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }    

    public void consume()
    {
        lock.lock();

        try
        {
            System.out.println("Starting consumer.");
            System.out.println("Ending consumer.");
            condition.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
}

class Synch_lcoks_test
{
    public static void main(String args[]) 
    {
        Synch_locks s = new Synch_locks();

        Thread producer = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                s.produce();
            }    
        });

        Thread consumer = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                s.consume();
            }    
        });

        producer.start();

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        consumer.start();
    }
}