/* 2 threads, one prints odd numbers and the other prints even numbers using wait(), notify() alternatively
 */

class Odd_even_print
{
    private int counter;
    private int limit;

    Odd_even_print(int limit)
    {
        counter = 0;
        this.limit = limit;
    }

    public void increment()
    {
        synchronized(this)
        {
            while(counter <= limit)
            {
                System.out.println("Currently executing thread: " + Thread.currentThread().getName() + " counter: " + counter);
                notify();
                ++counter;

                if(counter <= limit)
                {
                    try
                    {
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
}

class Test4
{
    public static void main(String args[])
    {
        Odd_even_print e = new Odd_even_print(11);

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