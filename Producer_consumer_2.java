import java.util.ArrayList;
import java.util.List;

public class Producer_consumer_2
{
    List<Integer> items = new ArrayList<Integer>(); // common resource
    final Object lock = new Object();

    public void produce()
    {
        synchronized(lock)
        {
            while(true)
            {
                if(items.isEmpty())
                {
                    items.add(0);
                    lock.notify();
                }
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
                        lock.wait();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void consume()
    {
        synchronized(lock)
        {
            while(true)
            {
                if(items.size() == 5)
                    lock.notify();

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
                        lock.wait();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class Prod_cons_test_1
{
    public static void main(String args[])
    {
        Producer_consumer_2 pc = new Producer_consumer_2();
        
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
        t2.start();
    }
}