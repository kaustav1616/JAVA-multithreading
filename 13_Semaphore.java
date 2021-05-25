import java.util.concurrent.Semaphore;

class Downloader
{
    private Semaphore semaphore = new Semaphore(3, true);

    public void downloadData()
    {
        try
        {
            semaphore.acquire();
            download();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            semaphore.release();
        }
    }

    private void download()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("Thread " + Thread.currentThread().getName() + " has finished downloading.");
    }
}

class SemaphoreTest
{
    public static void main(String[] args) 
    {
        Downloader downloader = new Downloader();
        
        for(int i = 0; i < 12; ++i)
        {
            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    downloader.downloadData();
                }    
            });

            t.start();
        }
    }
}