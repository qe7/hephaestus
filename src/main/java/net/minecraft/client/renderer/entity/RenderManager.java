// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFish;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import java.util.*;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            EntitySpider, RenderSpider, EntityPig, RenderPig, 
//            ModelPig, EntitySheep, RenderSheep, ModelSheep2, 
//            ModelSheep1, EntityCow, RenderCow, ModelCow, 
//            EntityWolf, RenderWolf, ModelWolf, EntityChicken, 
//            RenderChicken, ModelChicken, EntityCreeper, RenderCreeper, 
//            EntitySkeleton, RenderBiped, ModelSkeleton, EntityZombie, 
//            ModelZombie, EntitySlime, RenderSlime, ModelSlime, 
//            EntityPlayer, RenderPlayer, EntityGiantZombie, RenderGiantZombie, 
//            EntityGhast, RenderGhast, EntitySquid, RenderSquid, 
//            ModelSquid, EntityLiving, RenderLiving, ModelBiped, 
//            Entity, RenderEntity, EntityPainting, RenderPainting, 
//            EntityArrow, RenderArrow, EntitySnowball, RenderSnowball, 
//            Item, EntityEgg, EntityFireball, RenderFireball, 
//            EntityItem, RenderItem, EntityTNTPrimed, RenderTNTPrimed, 
//            EntityFallingSand, RenderFallingSand, EntityMinecart, RenderMinecart, 
//            EntityBoat, RenderBoat, EntityFish, RenderFish, 
//            EntityLightningBolt, RenderLightningBolt, Render, MathHelper, 
//            World, Block, FontRenderer, RenderEngine, 
//            ItemRenderer, GameSettings

public class RenderManager
{

    private RenderManager()
    {
        entityRenderMap = new HashMap();
        entityRenderMap.put(net.minecraft.entity.monster.EntitySpider.class, new RenderSpider());
        entityRenderMap.put(net.minecraft.entity.passive.EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
        entityRenderMap.put(net.minecraft.entity.passive.EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
        entityRenderMap.put(net.minecraft.entity.passive.EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
        entityRenderMap.put(net.minecraft.entity.passive.EntityWolf.class, new RenderWolf(new ModelWolf(), 0.5F));
        entityRenderMap.put(net.minecraft.entity.passive.EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
        entityRenderMap.put(net.minecraft.entity.monster.EntityCreeper.class, new RenderCreeper());
        entityRenderMap.put(net.minecraft.entity.monster.EntitySkeleton.class, new RenderBiped(new ModelSkeleton(), 0.5F));
        entityRenderMap.put(net.minecraft.entity.monster.EntityZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
        entityRenderMap.put(net.minecraft.entity.monster.EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
        entityRenderMap.put(net.minecraft.entity.player.EntityPlayer.class, new RenderPlayer());
        entityRenderMap.put(net.minecraft.entity.monster.EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6F));
        entityRenderMap.put(net.minecraft.entity.monster.EntityGhast.class, new RenderGhast());
        entityRenderMap.put(net.minecraft.entity.passive.EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7F));
        entityRenderMap.put(net.minecraft.entity.EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5F));
        entityRenderMap.put(net.minecraft.entity.Entity.class, new RenderEntity());
        entityRenderMap.put(net.minecraft.entity.item.EntityPainting.class, new RenderPainting());
        entityRenderMap.put(net.minecraft.entity.projectile.EntityArrow.class, new RenderArrow());
        entityRenderMap.put(net.minecraft.entity.projectile.EntitySnowball.class, new RenderSnowball(Item.snowball.getIconFromDamage(0)));
        entityRenderMap.put(net.minecraft.entity.projectile.EntityEgg.class, new RenderSnowball(Item.egg.getIconFromDamage(0)));
        entityRenderMap.put(net.minecraft.entity.projectile.EntityFireball.class, new RenderFireball());
        entityRenderMap.put(net.minecraft.entity.item.EntityItem.class, new RenderItem());
        entityRenderMap.put(net.minecraft.entity.item.EntityTNTPrimed.class, new RenderTNTPrimed());
        entityRenderMap.put(net.minecraft.entity.item.EntityFallingSand.class, new RenderFallingSand());
        entityRenderMap.put(net.minecraft.entity.item.EntityMinecart.class, new RenderMinecart());
        entityRenderMap.put(net.minecraft.entity.item.EntityBoat.class, new RenderBoat());
        entityRenderMap.put(net.minecraft.entity.projectile.EntityFish.class, new RenderFish());
        entityRenderMap.put(net.minecraft.entity.effect.EntityLightningBolt.class, new RenderLightningBolt());
        Render render;
        for(Iterator iterator = entityRenderMap.values().iterator(); iterator.hasNext(); render.setRenderManager(this))
        {
            render = (Render)iterator.next();
        }

    }

    public Render getEntityClassRenderObject(Class class1)
    {
        Render render = (Render)entityRenderMap.get(class1);
        if(render == null && class1 != (net.minecraft.entity.Entity.class))
        {
            render = getEntityClassRenderObject(class1.getSuperclass());
            entityRenderMap.put(class1, render);
        }
        return render;
    }

    public Render getEntityRenderObject(Entity entity)
    {
        return getEntityClassRenderObject(entity.getClass());
    }

    public void cacheActiveRenderInfo(World world, RenderEngine renderengine, FontRenderer fontrenderer, EntityLiving entityliving, GameSettings gamesettings, float f)
    {
        worldObj = world;
        renderEngine = renderengine;
        options = gamesettings;
        livingPlayer = entityliving;
        fontRenderer = fontrenderer;
        if(entityliving.isPlayerSleeping())
        {
            int i = world.getBlockId(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));
            if(i == Block.blockBed.blockID)
            {
                int j = world.getBlockMetadata(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));
                int k = j & 3;
                playerViewY = k * 90 + 180;
                playerViewX = 0.0F;
            }
        } else
        {
            playerViewY = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f;
            playerViewX = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f;
        }
        field_1222_l = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * (double)f;
        field_1221_m = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double)f;
        field_1220_n = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * (double)f;
    }

    public void renderEntity(Entity entity, float f)
    {
        double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f;
        float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        float f2 = entity.getEntityBrightness(f);
        GL11.glColor3f(f2, f2, f2);
        renderEntityWithPosYaw(entity, d - renderPosX, d1 - renderPosY, d2 - renderPosZ, f1, f);
    }

    public void renderEntityWithPosYaw(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        Render render = getEntityRenderObject(entity);
        if(render != null)
        {
            render.doRender(entity, d, d1, d2, f, f1);
            render.doRenderShadowAndFire(entity, d, d1, d2, f, f1);
        }
    }

    public void func_852_a(World world)
    {
        worldObj = world;
    }

    public double func_851_a(double d, double d1, double d2)
    {
        double d3 = d - field_1222_l;
        double d4 = d1 - field_1221_m;
        double d5 = d2 - field_1220_n;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public FontRenderer getFontRenderer()
    {
        return fontRenderer;
    }

    private Map entityRenderMap;
    public static RenderManager instance = new RenderManager();
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public RenderEngine renderEngine;
    public ItemRenderer itemRenderer;
    public World worldObj;
    public EntityLiving livingPlayer;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double field_1222_l;
    public double field_1221_m;
    public double field_1220_n;

}
