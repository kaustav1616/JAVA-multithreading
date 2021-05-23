import java.util.ArrayList;
import java.util.List;

public class Producer_consumer 
{
    List<Integer> items = new ArrayList<Integer>(); // common resource
    final Object lock = new Object();

    public void produce()
    {
        synchronized(lock)
        {
            while(true)
            {
                if(items.size() == 5) // buffer is full; cannot produce any more
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
                else
                {
                    items.add(items.size() + 1);
                    System.out.println("Produced: " + items.get(items.size() - 1));

                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    if(items.size() == 1)
                        lock.notify();
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
                if(items.size() == 0) // buffer is empty; nothing to consume
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
                else
                {
                    items.remove(items.size() - 1);
                    System.out.println("Consumed: " + (items.size() + 1));
                }

                try
                {
                    Thread.sleep(2000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }

                if(items.size() == 4)
                    lock.notify();
            }
        }
    }
}

class Prod_cons_test
{
    public static void main(String args[])
    {
        Producer_consumer pc = new Producer_consumer();
        
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