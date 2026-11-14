// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.network;


// Referenced classes of package net.minecraft.src:
//            NetworkManager

public class NetworkReaderThread extends Thread
{

    public NetworkReaderThread(NetworkManager networkmanager, String s)
    {
        super(s);
        netManager = networkmanager;
    }

    public void run()
    {
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numReadThreads++;
        }
        try
        {
            while(NetworkManager.isRunning(netManager) && !NetworkManager.isServerTerminating(netManager)) 
            {
                while(NetworkManager.readNetworkPacket(netManager)) ;
                try
                {
                    sleep(100L);
                }
                catch(InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            synchronized(NetworkManager.threadSyncObject)
            {
                NetworkManager.numReadThreads--;
            }
        }
    }

    public final NetworkManager netManager; /* synthetic field */
}
