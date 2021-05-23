/* Since threads share the common heap (where created objects are stored) memory of the process,
 * there may be synchronization issues. This demonstrates how to overcome these issues with 'synchronized' keyword
 */

/* demonstrates need for synchronization with intrinsic lock (monitor)
 * to see demonstration, check difference with and w/o 'synchronized' keyword in increment() function
 */

class Synchronization_intro
{
    private int counter;

    Synchronization_intro()
    {
        counter = 0;
    }
    
    // with 'synchronized' keyword, method is executed by only one thread at a time
    public synchronized void increment()
    {
        ++counter;
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

class Test_Synch
{
    public static void main(String args[])
    {
        int counter, problem_count = 0;

        // testing 100 times to see if 'counter' value is as expected
        for(int i = 0; i <= 99; ++i)
        {
            Synchronization_intro s = new Synchronization_intro();
            counter = s.process();

            if(counter != 200)
            {
                System.out.println("counter = " + counter);
                ++problem_count;
            }
        }

        System.out.println("Number of times synchronization issues: " + problem_count);
    }
}