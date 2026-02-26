package com.atticl.hbmpanic;

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
                Item cleanSyringe = (Item) Item.itemRegistry.getObject("hbm:item.syringe_empty");
                player.inventory.setInventorySlotContents(slot, new ItemStack(cleanSyringe));
                world.playSoundAtEntity(player, "random.fizz", 0.7F, 1.5F);
                return;
            }

            if (world.getTotalWorldTime() % 20 == 0) {
            	net.minecraft.nbt.NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            	if (nbt != null) {
            	    float currentRads = nbt.getFloat("rad");
            	    nbt.setFloat("rad", currentRads + 0.5F);
            	}
                
                world.playSoundAtEntity(player, "random.click", 0.4F, 2.0F);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        World world = entityItem.worldObj;
        if (!world.isRemote && entityItem.isInsideOfMaterial(Material.water)) {
            Item cleanSyringe = (Item) Item.itemRegistry.getObject("hbm:item.syringe_empty");
            
            EntityItem newEntity = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(cleanSyringe));
            world.spawnEntityInWorld(newEntity);
            
            world.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ, "random.fizz", 0.7F, 1.5F);
            
            entityItem.setDead();
            return true;
        }
        return false;
    }
}