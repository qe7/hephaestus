// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.stats;

import java.util.Map;

// Referenced classes of package net.minecraft.src:
//            StatsSyncher

public class ThreadStatSyncherSend extends Thread
{

    public ThreadStatSyncherSend(StatsSyncher statssyncher, Map map)
    {
//        super();
        field_27232_b = statssyncher;
        field_27233_a = map;
    }

    public void run()
    {
        try
        {
            StatsSyncher.func_27412_a(field_27232_b, field_27233_a, StatsSyncher.func_27414_e(field_27232_b), StatsSyncher.func_27417_f(field_27232_b), StatsSyncher.func_27419_g(field_27232_b));
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            StatsSyncher.func_27416_a(field_27232_b, false);
        }
    }

    public final Map field_27233_a; /* synthetic field */
    public final StatsSyncher field_27232_b; /* synthetic field */
}
