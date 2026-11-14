package com.github.qe7.hephaestus.modules.impl.combat;

import com.github.qe7.hephaestus.events.EventSubscriber;
import com.github.qe7.hephaestus.events.Listener;
import com.github.qe7.hephaestus.events.impl.game.MotionEvent;
import com.github.qe7.hephaestus.modules.AbstractModule;
import com.github.qe7.hephaestus.modules.ModuleCategory;
import com.github.qe7.hephaestus.settings.impl.BooleanSetting;
import com.github.qe7.hephaestus.settings.impl.ListSetting;
import com.github.qe7.hephaestus.settings.impl.ModeSetting;
import com.github.qe7.hephaestus.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KillAuraModule extends AbstractModule {

    private enum Mode {
        SINGLE,
        MULTI
    }

    private final ModeSetting<Mode> mode = new ModeSetting<>("Mode", Mode.MULTI);
    private final ListSetting<Class<? extends Entity>> targets = ListSetting.entities("Targets", EntityLiving.class);
    private final NumberSetting<Double> range = new NumberSetting<>("Range", 4.0, 1.0, 6.0, 0.1);
    private final NumberSetting<Integer> delay = new NumberSetting<>("Delay", 10, 1, 20, 1);
    private final BooleanSetting rotate = new BooleanSetting("Rotate", false);

    private final List<EntityLiving> inRange = new ArrayList<>();
    private int ticksSinceAttack;

    public KillAuraModule() {
        super("KillAura", ModuleCategory.COMBAT);

        this.description = "Attacks nearby entities.";
    }

    @Override
    protected void onDisable() {
        inRange.clear();
    }

    @EventSubscriber
    private final Listener<MotionEvent.Pre> preListener = event -> {
        findTargets();

        if (!rotate.getValue() || inRange.isEmpty()) return;

        final Minecraft mc = Minecraft.get();
        final EntityLiving target = inRange.get(0);

        final double dx = target.posX - mc.thePlayer.posX;
        final double dz = target.posZ - mc.thePlayer.posZ;
        final double dy = (target.boundingBox.minY + target.boundingBox.maxY) / 2.0 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());

        event.setYaw((float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
        event.setPitch((float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz))));
    };

    @EventSubscriber
    private final Listener<MotionEvent.Post> postListener = event -> {
        ticksSinceAttack = Math.min(ticksSinceAttack + 1, delay.getMax());

        if (inRange.isEmpty() || ticksSinceAttack < delay.getValue()) return;

        final Minecraft mc = Minecraft.get();
        mc.thePlayer.swingItem();

        for (EntityLiving target : mode.getValue() == Mode.SINGLE ? inRange.subList(0, 1) : inRange) {
            mc.playerController.attackEntity(mc.thePlayer, target);
        }
        ticksSinceAttack = 0;
    };

    private void findTargets() {
        final Minecraft mc = Minecraft.get();
        inRange.clear();

        for (Object object : mc.theWorld.loadedEntityList) {
            if (!(object instanceof EntityLiving) || object == mc.thePlayer) continue;

            final EntityLiving entity = (EntityLiving) object;
            if (entity.isDead || entity.health <= 0 || mc.thePlayer.getDistanceToEntity(entity) > range.getValue() || !isTarget(entity)) continue;

            inRange.add(entity);
        }
        inRange.sort(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)));
    }

    private boolean isTarget(Entity entity) {
        for (Class<? extends Entity> type : targets.getValue()) {
            if (type.isInstance(entity)) return true;
        }
        return false;
    }
}
