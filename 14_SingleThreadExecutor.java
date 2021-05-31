import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable
{
    int id;

    Task(int id)
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

class TestSingleThreadExecutor
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        for(int i = 0; i < 5; ++i)
            executor.execute(new Task(i + 1));

            executor.shutdown();
    }
}