import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task1 implements Runnable
{
    int id;

    Task1(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public void run()
    {
        System.out.println("Executing task " + id + " by thread " + Thread.currentThread().getName());

        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Completed task " + id + " by thread " + Thread.currentThread().getName());
    }
}

class TestFixedThreadPool
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        for(int i = 0; i < 5; ++i)
            executor.execute(new Task1(i + 1));

        executor.shutdown();
    }
}