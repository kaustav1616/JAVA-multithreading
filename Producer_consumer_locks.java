import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producer_consumer_locks
{
    private List<Integer> items = new ArrayList<Integer>(); // common resource
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce()
    {
        lock.lock();

        try
        {
            while(true)
            {
                if(items.size() == 0)
                {
                    condition.signal();
                }
                    
                if(items.isEmpty())
                    items.add(0);
                else
                    items.add(items.get(items.size() - 1) + 1);
                            
                System.out.println("Added: " + items.get(items.size() - 1));
        
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
    
                if(items.size() == 5)
                {
                    try
                    {
                        condition.await();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
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
            while(true)
            {
                if(items.size() == 5)
                {
                    condition.signal();
                }
    
                int removed = items.remove(items.size() - 1);
                System.out.println("Removed: " + removed);
    
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
    
                if(items.size() == 0)
                {
                    try
                    {
                        condition.await();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        finally
        {
            lock.unlock();
        }
    }
}

class Prod_cons_test_2
{
    public static void main(String args[])
    {
        Producer_consumer_locks pc = new Producer_consumer_locks();
        
        Thread t1 = new Thread(new Runnable() // producer thread
        {
            @Override
            public void run()
            {
                pc.produce();
            }
        });

        Thread t2 = new Thread(new Runnable() // consumer thread
        {
            @Override
            public void run()
            {
                pc.consume();
            }
        });

        t1.start();

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
        t2.start();
    }
}