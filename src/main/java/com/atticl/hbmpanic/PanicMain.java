package com.atticl.hbmpanic;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = PanicMain.MODID, version = PanicMain.VERSION, name = PanicMain.NAME, dependencies = "required-after:hbm")
public class PanicMain {
	
	public static final String MODID = "hbmpanic";
	public static final String NAME = "HBM Panic Injector";
	public static final String VERSION = "1.0.0";
	
	@Mod.Instance(MODID)
	public static PanicMain instance;
	
	public static Item panicInjector;
	public static Item spentSyringe;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("Panic Injector: If only it worked.");
		
		panicInjector = new ItemPanicInjector();
		spentSyringe = new ItemSpentSyringe();
		
		GameRegistry.registerItem(panicInjector, "panic_injector");
		GameRegistry.registerItem(spentSyringe, "spent_syringe");
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Panic Injector: Hooking directly into HBM...");
        
        Item hbmSyringe = GameRegistry.findItem("hbm", "item.syringe_empty"); 
        Item hbmChemical = GameRegistry.findItem("hbm", "item.powder_schrabidium"); 

        if (hbmSyringe == null) {
            System.out.println("CRITICAL ERROR: HBM Syringe is NULL! The registry name is wrong.");
        }
        if (hbmChemical == null) {
            System.out.println("CRITICAL ERROR: HBM Chemical is NULL! The registry name is wrong.");
        }

        if (hbmSyringe != null && hbmChemical != null) {
            GameRegistry.addRecipe(new ItemStack(panicInjector), 
                " C ", 
                " S ", 
                "   ", 
                'C', hbmChemical,
                'S', hbmSyringe
            );
            System.out.println("Panic Injector: HBM Recipe successfully registered!");
        } else {
            System.out.println("Panic Injector: Skipping recipe registration to prevent crash.");
        }
    }
}
