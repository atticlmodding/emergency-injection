package com.atticl.hbmpanic;

import com.hbm.extprop.HbmLivingProps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


public class ItemPanicInjector extends Item {
	
	public ItemPanicInjector() {
		this.setUnlocalizedName("panic_injector");
		this.setTextureName("hbmpanic:panic_injector");
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			HbmLivingProps.incrementRadiation(player, -250.0F);
		    
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 1200, 1));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 1200, 2));
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, 1200, 1));
            
            if (!player.capabilities.isCreativeMode) {
            	--stack.stackSize;
            	ItemStack spent = new ItemStack(PanicMain.spentSyringe);
            	
            	if (stack.stackSize <= 0) {
            		return spent;
            	} else {
            		if (!player.inventory.addItemStackToInventory(spent)) {
            			player.dropPlayerItemWithRandomChoice(spent, false);
            		}
            	}
            }
            
            world.playSoundAtEntity(player,  "mob.irongolem.hit", 1.0F, 0.5F);
		}
		
		return stack;
	}
}
