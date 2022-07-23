package com.fgtXray.fgtXray;

import com.fgtXray.fgtXray.client.OresSearch;
import com.fgtXray.fgtXray.client.gui.helper.HelperBlock;
import com.fgtXray.fgtXray.config.ConfigHandler;
import com.fgtXray.fgtXray.config.DefaultConfig;
//import com.fgtXray.fgtXray.helper.ItemStackHelper;
import com.fgtXray.fgtXray.proxy.ServerProxy;
import com.fgtXray.fgtXray.reference.OreInfo;
import com.fgtXray.fgtXray.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.registry.ForgeRegistries;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME, version=Reference.MOD_VERSION)
public class FgtXRay
{
	public static int localPlyX, localPlyY, localPlyZ, localPlyXPrev, localPlyZPrev; // For internal use in the ClientTick thread.
	public static boolean drawOres = false; // Off by default
	public static ArrayList<HelperBlock> blockList = new ArrayList<>();

	public static ArrayList<OreInfo> searchList = new ArrayList<>(); // List of ores/blocks to search for.

	public static final String[] distStrings = new String[] // Strings for use in the GUI Render Distance button
		{ "8", "16", "32", "48", "64", "80", "128", "256" };
    public static final int[] distNumbers = new int[] // Radius +/- around the player to search. So 8 is 8 on left and right of player plus under the player. So 17x17 area.
		{8, 16, 32, 48, 64, 80, 128, 256};

    public static int currentDist = 0; // Index for the distNumers array. Default search distance.

	// Keybindings
	public static final int keyIndex_toggleXray = 0;
	public static final int keyIndex_showXrayMenu = 1;
	public static final int[] keyBind_keyValues = 
	{
		Keyboard.KEY_BACKSLASH,
		Keyboard.KEY_Z
	};
	public static final String[] keyBind_descriptions =
	{
		"Toggle X-Ray",
		"Open X-Ray Menu"
	};
	public static KeyBinding[] keyBind_keys = null;

	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static FgtXRay instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.xray.client.proxy.ClientProxy", serverSide="com.fgtXray.fgtXray.proxy.ServerProxy")
	private static ServerProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
    {
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		config.load();
		
		if( config.getCategoryNames().isEmpty() )
        {
			System.out.println("[XRay] Config file not found. Creating now.");
			DefaultConfig.create( config );
			config.save();
		}
		
		ConfigHandler.setup( event ); // Read the config file and setup environment.
        System.out.println("[XRay] PreInit ");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
    {
		proxy.proxyInit();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
    {
        //// TODO: Find 1.7.10 alternative to ForgeRegistries
		// for ( Block block : ForgeRegistries.BLOCKS ) {
		// 	List<ItemStack> subBlocks = new ArrayList<>();
		// 	block.getSubBlocks( new ItemStack( block ).getItem(), block.getCreativeTabToDisplayOn(), subBlocks );

		// 	for( ItemStack subBlock : subBlocks ) {
		// 		if( ItemStackHelper.isEmpty(subBlock) )
		// 			continue;

		// 		Block tmpBlock = Block.getBlockFromItem( subBlock.getItem() );
		// 		blockList.add( new HelperBlock( subBlock.getDisplayName(), tmpBlock, subBlock, subBlock.getItem(), subBlock.getItem().getRegistryName() ));
		// 	}
		// }

	}
}