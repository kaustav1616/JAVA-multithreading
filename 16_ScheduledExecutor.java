import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task2 implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("Doing some work");

        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Done work");
    }
}

class TestScheduledExecutor
{
    public static void main(String[] args)
    {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        executor.scheduleAtFixedRate(new Task2(), 0, 1, TimeUnit.SECONDS);
    }
}