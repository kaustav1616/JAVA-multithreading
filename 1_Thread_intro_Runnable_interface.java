/* demo of threading with Collections interface */

class Abc
{
    public static void main(String [] args)
    {
        // multithreading (time-slicing)
        Thread t1 = new Thread(new Runner1());
        t1.start();
        Thread t2 = new Thread(new Runner2());
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

class Runner1 implements Runnable
{
    @Override
    public void run()
    {
        for(int i = 0; i < 10; ++i)
            System.out.println("Runner 1: " + i);
    }
}

class Runner2 implements Runnable
{
    @Override
    public void run()
    {
        int i;

        for(i = 0; i <= 9; ++i)
            System.out.println("Runner 2: " + i);
    }
}