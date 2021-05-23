// 2 threads, one prints odd numbers and the other prints even numbers

public class Odd_even
{
    private int counter = 0;

    public void increment()
    {
        synchronized(this)
        {
            while(counter <= 3)
            {
                System.out.print("Currently executing thread: " + Thread.currentThread().getName());
                System.out.println(" ; counter: " + counter);
                
                if(counter != 0)
                    notify();

                ++counter;

                try
                {
                    if(counter <= 3)
                        wait();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Test4
{
    public static void main(String args[])
    {
        Odd_even e = new Odd_even();

        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                e.increment();
            }
        });
    
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                e.increment();
            }
        });
    
        t1.start();
        t2.start();
    }
}