// demonstrates use of 'volatile' keyword and also demonstrates how to stop a thread from an external source

class Volatile_keyword 
{
    public static void main(String[] args)
    {
        Worker w = new Worker();
        Thread t1 = new Thread(w);
        t1.start();

        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        w.setTerminated(true);
    }
}

class Worker implements Runnable
{
    // 'volatile' keyword ensures that variable is not cached by thread and read from common memory every time
    private volatile boolean terminated;

    Worker()
    {
        terminated = false;
    }

    @Override
    public void run()
    {
        while(!terminated)
        {
            System.out.println("Worker is working");

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Worker is terminated");
    }

    public void setTerminated(boolean terminated)
    {
        this.terminated = terminated;
    }

    public boolean getTerminated()
    {
        return terminated;
    }
}