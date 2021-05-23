import java.util.concurrent.*;

class DownloadData 
{
    Semaphore semaphore = new Semaphore(3, true);

    public void downloadData() 
    {
        try 
        {
            semaphore.acquire();
            download();
        } 
        catch (InterruptedException e) 
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
        System.out.println("Downloading data..");

        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
	}
}

public class SemaphoreTest
{
    public static void main(String[] args) 
    {
        DownloadData downloadData = new DownloadData();
        
        for(int i = 0; i < 12; ++i)
        {
            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    downloadData.downloadData();
                }    
            });

            t.start();
        }
    }
}