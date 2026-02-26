package com.atticl.hbmpanic;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpentSyringe extends Item {

    public ItemSpentSyringe() {
        this.setUnlocalizedName("spent_syringe");
        this.setTextureName("hbmpanic:spent_syringe");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, java.util.List list, boolean bool) {
    	list.add("\u00A74Highly Radioactive");
    	list.add("\u00A77Wash in water to decontaminate.");
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            // 1. Water Check (Always first)
            if (player.isInsideOfMaterial(net.minecraft.block.material.Material.water)) {
                player.inventory.setInventorySlotContents(slot, new ItemStack(ModItems.syringe_empty));
                world.playSoundAtEntity(player, "random.fizz", 0.7F, 1.5F);
                return;
            }

            if (world.getTotalWorldTime() % 20 == 0) {
            	HbmLivingProps.incrementRadiation(player, 0.5F);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        World world = entityItem.worldObj;
        if (!world.isRemote && entityItem.isInsideOfMaterial(Material.water)) {
            EntityItem newEntity = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(ModItems.syringe_empty));
            world.spawnEntityInWorld(newEntity);
            
            world.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "random.fizz", 0.7F, 1.5F);
            
            entityItem.setDead();
            return true;
        }
        return false;
    }
}