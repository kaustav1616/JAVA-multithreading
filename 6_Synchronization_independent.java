/* To demonstrate problem of single object / class level intrinsic lock
 * 2 functions working on 2 different data items need to be synchronized
 * (but they are not in conflict with each other)..but since there is only 1 lock,
 * the 2 methods cannot be executed concurrently.
 * In essence, problem is that 2 independent blocks of code that require synchronization cannot be executed independently
 * of each other due to single intrinsic lock. This leads to increased execution time. 
 * To demonstrate, check diff in processing time with and w/o synchronization for increment1() and increment2()
 */

class Synchronization_independent
{
    private int counter1 = 0, counter2 = 0;;

    // method can only be executed by a single method at a given time
    // since Synchronization object has single lock, increment1() and increment2() can't be accessed concurrently
    public void increment1()
    {
        ++counter1;
    }

    public void increment2()
    {
        ++counter2;
    }

    public long process()
    {
        long startTime, endTime;

        startTime = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 999999; ++i)
                    increment1();
            }
        });
    
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 999999; ++i)
                    increment2();
            }
        });
    
        t1.start();
        t2.start();

        try
        {
            t1.join();
            t2.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        if(counter1 != 1000000 || counter2 != 1000000)
            System.out.println("Error.");
            
        endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }
}

class Test
{
    public static void main(String args[])
    {
        long time = 0, avgTime;

        for(int i = 0; i <= 99; ++i)
        {
            Synchronization_independent s = new Synchronization_independent();
            time += s.process();
        }

        avgTime = time / 100;
        System.out.println("Time required: " + avgTime);
    }
}