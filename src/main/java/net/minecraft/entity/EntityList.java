// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.entity;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package net.minecraft.src:
//            World, Entity, NBTTagCompound, EntityArrow, 
//            EntitySnowball, EntityItem, EntityPainting, EntityLiving, 
//            EntityMob, EntityCreeper, EntitySkeleton, EntitySpider, 
//            EntityGiantZombie, EntityZombie, EntitySlime, EntityGhast, 
//            EntityPigZombie, EntityPig, EntitySheep, EntityCow, 
//            EntityChicken, EntitySquid, EntityWolf, EntityTNTPrimed, 
//            EntityFallingSand, EntityMinecart, EntityBoat

public class EntityList
{

    public EntityList()
    {
    }

    private static void addMapping(Class class1, String s, int i)
    {
        stringToClassMapping.put(s, class1);
        classToStringMapping.put(class1, s);
        IDtoClassMapping.put(Integer.valueOf(i), class1);
        classToIDMapping.put(class1, Integer.valueOf(i));
    }

    public static Entity createEntityInWorld(String s, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)stringToClassMapping.get(s);
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    net.minecraft.world.World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Entity createEntityFromNBT(NBTTagCompound nbttagcompound, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)stringToClassMapping.get(nbttagcompound.getString("id"));
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    net.minecraft.world.World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(entity != null)
        {
            entity.readFromNBT(nbttagcompound);
        } else
        {
            System.out.println((new StringBuilder()).append("Skipping Entity with id ").append(nbttagcompound.getString("id")).toString());
        }
        return entity;
    }

    public static Entity createEntity(int i, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)IDtoClassMapping.get(Integer.valueOf(i));
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    net.minecraft.world.World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(entity == null)
        {
            System.out.println((new StringBuilder()).append("Skipping Entity with id ").append(i).toString());
        }
        return entity;
    }

    public static int getEntityID(Entity entity)
    {
        return ((Integer)classToIDMapping.get(entity.getClass())).intValue();
    }

    public static Map getClassToStringMapping()
    {
        return classToStringMapping;
    }

    public static String getEntityString(Entity entity)
    {
        return (String)classToStringMapping.get(entity.getClass());
    }

    private static Map stringToClassMapping = new HashMap();
    private static Map classToStringMapping = new HashMap();
    private static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();

    static 
    {
        addMapping(net.minecraft.entity.projectile.EntityArrow.class, "Arrow", 10);
        addMapping(net.minecraft.entity.projectile.EntitySnowball.class, "Snowball", 11);
        addMapping(net.minecraft.entity.item.EntityItem.class, "Item", 1);
        addMapping(net.minecraft.entity.item.EntityPainting.class, "Painting", 9);
        addMapping(net.minecraft.entity.EntityLiving.class, "Mob", 48);
        addMapping(net.minecraft.entity.monster.EntityMob.class, "Monster", 49);
        addMapping(net.minecraft.entity.monster.EntityCreeper.class, "Creeper", 50);
        addMapping(net.minecraft.entity.monster.EntitySkeleton.class, "Skeleton", 51);
        addMapping(net.minecraft.entity.monster.EntitySpider.class, "Spider", 52);
        addMapping(net.minecraft.entity.monster.EntityGiantZombie.class, "Giant", 53);
        addMapping(net.minecraft.entity.monster.EntityZombie.class, "Zombie", 54);
        addMapping(net.minecraft.entity.monster.EntitySlime.class, "Slime", 55);
        addMapping(net.minecraft.entity.monster.EntityGhast.class, "Ghast", 56);
        addMapping(net.minecraft.entity.monster.EntityPigZombie.class, "PigZombie", 57);
        addMapping(net.minecraft.entity.passive.EntityPig.class, "Pig", 90);
        addMapping(net.minecraft.entity.passive.EntitySheep.class, "Sheep", 91);
        addMapping(net.minecraft.entity.passive.EntityCow.class, "Cow", 92);
        addMapping(net.minecraft.entity.passive.EntityChicken.class, "Chicken", 93);
        addMapping(net.minecraft.entity.passive.EntitySquid.class, "Squid", 94);
        addMapping(net.minecraft.entity.passive.EntityWolf.class, "Wolf", 95);
        addMapping(net.minecraft.entity.item.EntityTNTPrimed.class, "PrimedTnt", 20);
        addMapping(net.minecraft.entity.item.EntityFallingSand.class, "FallingSand", 21);
        addMapping(net.minecraft.entity.item.EntityMinecart.class, "Minecart", 40);
        addMapping(net.minecraft.entity.item.EntityBoat.class, "Boat", 41);
    }
}
