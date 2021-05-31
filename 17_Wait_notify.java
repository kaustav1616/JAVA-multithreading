/* wait(): thread releases lock and goes into waiting state
 * notify(): thread releases lock and notifies a thread waiting ON THE SAME LOCK
 * that it can once again acquire the lock
 * In this demo, t1 starts and then goes into the waiting state, the t2 starts and then goes into the waiting state
 * and then t3 starts and before completing execution, wakes up the first waiting thread in the queue (t1), which
 * executes and just before completion, wakes up the first waiting thread in the queue (t2), and finally t2 completes execution
 */

class Wait_notify 
{
    public void f1() throws InterruptedException
    {
        synchronized(this)
        {
            System.out.println("Starting f1.");
            wait();
            System.out.println("Ending f1.");
            notify();
        }
    }

    public void f2() throws InterruptedException
    {
        synchronized(this)
        {
            System.out.println("Starting f2.");
            wait();
            System.out.println("Ending f2.");
        }
    }

    public void f3() throws InterruptedException
    {
        synchronized(this)
        {
            System.out.println("Starting f3.");
            System.out.println("Ending f3.");
            notify();
        }
    }

    public void process()
    {
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    f1();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    f2();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    f3();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
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

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        t3.start();
    }
}

class Test3
{
    public static void main(String args[])
    {
        Wait_notify w = new Wait_notify();
        w.process();
    }
}