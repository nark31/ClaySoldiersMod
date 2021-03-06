/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;

public class UpgradeRedstone
        extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort(NBT_USES, (short) 4);
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        if( target.addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_REDSTONE)) != null ) {
            target.playSound("random.fizz", 1.0F, 1.0F);
            upgradeInst.getNbtTag().setShort(NBT_USES, (short) (upgradeInst.getNbtTag().getShort(NBT_USES) - 1));
        }
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return upgradeInst.getNbtTag().getShort(NBT_USES) == 0;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        if( stack.getItem() == Items.redstone ) {
            this.consumeItem(stack, upgradeInst);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.redstone_block) ) {
            clayMan.playSound("dig.stone", 1.0F, 1.0F);
        }
    }

    @Override
    public void onProjectileHit(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MovingObjectPosition target,
                                ISoldierProjectile<? extends EntityThrowable> projectile) {
        if( target.entityHit instanceof EntityClayMan ) {
            EntityClayMan caddicarus = (EntityClayMan) target.entityHit;
            if( caddicarus.addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_REDSTONE)) != null ) {
                caddicarus.playSound("random.fizz", 1.0F, 1.0F);
                upgradeInst.getNbtTag().setShort(NBT_USES, (short) (upgradeInst.getNbtTag().getShort(NBT_USES) - 1));
            }
        }
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
        // TODO: drop it when unused or?
        if( upgradeInst.getNbtTag().getShort(NBT_USES) == 4 ) {
            droppedItems.add(upgradeInst.getStoredItem());
        }
    }
}
