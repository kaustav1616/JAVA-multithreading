import java.util.concurrent.atomic.AtomicInteger;

public class Atomic_integer 
{
    private AtomicInteger counter = new AtomicInteger(0);
    
    public void increment() // no need for synchronized method / block
    {
        counter.getAndIncrement();
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

        return counter.get();
    }    
}

class Atomic_integer_test
{
    public static void main(String args[])
    {
        int counter, c = 0;

        for(int i = 0; i <= 99; ++i)
        {
            Atomic_integer a = new Atomic_integer();
            counter = a.process();

            if(counter != 200)
            {
                System.out.println("counter = " + counter);
                ++c;
            }
        }

        System.out.println("Number of times synchronization issues: " + c);
    }
}