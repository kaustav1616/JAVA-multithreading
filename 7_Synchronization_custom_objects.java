/* separate custom locking objects for independent blocks (to overcome issues of single class / object level intrinsic lock)
 * just like previous problem, check the difference with and without synchronized block
 */ 

class Synchronization_custom_objects 
{
    private int counter1, counter2;
    private final Object cust_lock_1 = new Object(), cust_lock_2 = new Object();

    Synchronization_custom_objects()
    {
        counter1 = 0;
        counter2 = 0;
    }

    /* since there are two separate locks, counter1 and counter2 can be accessed independently of each other,
     * but since both are synchronized, each of them can only be accessed by one thread at a time
     */
    public void increment1()
    {
        synchronized(cust_lock_1)
        {
            ++counter1;
        }
    }

    public void increment2()
    {
        synchronized(cust_lock_2)
        {
            ++counter2;
        }
    }

    public int[] process()
    {
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment1();
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment1();
            }
        });
    
        Thread t3 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment2();
            }
        });

        Thread t4 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0; i <= 99; ++i)
                    increment2();
            }
        });
    
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try
        {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        return new int[]{counter1, counter2};
    }    
}

class Test_1
{
    public static void main(String args[])
    {
        int counter1, counter2, problem_count1 = 0, problem_count2 = 0;
        int[] temp;

        // testing 100 times to see if 'counter' value is as expected
        for(int i = 0; i <= 99; ++i)
        {
            Synchronization_custom_objects s = new Synchronization_custom_objects();
            temp = s.process();
            counter1 = temp[0];
            counter2 = temp[1];

            if(counter1 != 200)
            {
                System.out.println("counter1 = " + counter1);
                ++problem_count1;
            }

            if(counter2 != 200)
            {
                System.out.println("counter2 = " + counter2);
                ++problem_count2;
            }
        }

        System.out.println("Number of times synchronization issues with counter1: " + problem_count1);
        System.out.println("Number of times synchronization issues with counter2: " + problem_count2);
    }
}