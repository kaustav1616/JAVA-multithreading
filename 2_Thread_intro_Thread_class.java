/* demo of threading with Thread class */

class Def
{
    public static void main(String[] args)
    {
        Thread t1 = new Runner3();
        t1.start();
        Thread t2 = new Runner4();
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

        System.out.println("Finished execution.");
        System.out.println(Thread.currentThread().getName());
    }
}

class Runner3 extends Thread
{
    @Override
    public void run()
    {
        for(int i = 0; i < 10; ++i)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Runner 3: " + i);
        }
    }
}

class Runner4 extends Thread
{
    @Override
    public void run()
    {
        int i;

        for(i = 0; i <= 9; ++i)
            System.out.println("Runner 4: " + i);
    }
}