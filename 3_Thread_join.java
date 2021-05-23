/* Thread.join() enforces a synchronous order on asynchronous operations
 * Here, we have two asynchronous operations: Download_images() and Process_images() that
 * need to be executed sequentially. The main() thread remains active while
 * another thread (Analyze_Images) takes care of executing these 2 operations.
 */

class Join_Test
{
    public static void main(String[] args)
    {
        Thread t = new Analyze_Images();
        t.start();

        // to demonstrate that main thread remains active
        for(int i = 0; i < 10; ++i)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Maintaining interactivity in the " + Thread.currentThread().getName() + " thread");
        }
    }
}

class Analyze_Images extends Thread
{
    @Override
    public void run()
    {
        Thread t1 = new Download_images();
        t1.start();

        try
        {
            t1.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
        Thread t2 = new Process_images();
        t2.start();
    }
}

class Process_images extends Thread
{
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Images processed.");
    }
}

class Download_images extends Thread
{
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Images downloaded.");
    }
}