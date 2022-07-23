package com.fgtXray.fgtXray.config;


import com.fgtXray.fgtXray.FgtXRay;
import com.fgtXray.fgtXray.reference.OreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

public class ConfigHandler
{
	private static Configuration config = null; // Save the config file handle for use later.
	private static Minecraft mc = Minecraft.getMinecraft();

	public static void setup(FMLPreInitializationEvent event )
	{
		config = new Configuration( event.getSuggestedConfigurationFile() );
		config.load();
		FgtXRay.currentDist = config.get(Configuration.CATEGORY_GENERAL, "searchdist", 0).getInt(); // Get our search distance.

		for( String category : config.getCategoryNames() ) // Iterate through each category in our config file.
		{
			ConfigCategory cat = config.getCategory( category );

			if( category.startsWith("ores.") )
			{
				String name = cat.get("name").getString();
				int id = cat.get("id").getInt();
				int meta = cat.get("meta").getInt();
				int[] color = {cat.get("red").getInt(), cat.get("green").getInt(), cat.get("blue").getInt()};
				boolean enabled = cat.get("enabled").getBoolean(false);

				FgtXRay.searchList.add( new OreInfo( name, name.replaceAll("\\s+", ""), id, meta, color, enabled ) );
			}
		}

		config.save();
	}

	public static void add( String oreName, Integer id, Integer meta, int[] color )
	{
		config.load();
		String cleanName = oreName.replaceAll("\\s+", "").toLowerCase();

		// check if entry exists
		for( String ignored : config.getCategoryNames() )
		{
			if(Objects.equals(config.get("ores." + cleanName, "name", "").getString(), cleanName))
			{
				String notify = String.format( "[XRay] %s already exists. Please enter a different name. ", oreName );
				mc.ingameGUI.getChatGUI().printChatMessage( new ChatComponentText(notify));
				return;
			}
		}

		for( String category : config.getCategoryNames() )
		{
			if( !category.startsWith("ores.") )
				continue;

			config.get("ores."+cleanName, "name", "").set(oreName);
			config.get("ores."+cleanName, "enabled", "false").set( true );
			config.get("ores."+cleanName, "id", "").set( id );
			config.get("ores."+cleanName, "meta", "").set( meta );
			config.get("ores."+cleanName, "red", "").set( color[0] );
			config.get("ores."+cleanName, "green", "").set( color[1] );
			config.get("ores."+cleanName, "blue", "").set( color[2] );
		}
		config.save();
	}

	// For updating single options
	public static void update(String string, boolean draw){
		if( string.equals("searchdist") ) // Save the new render distance.
		{
			config.get(Configuration.CATEGORY_GENERAL, "searchdist", 0).set( XRay.currentDist);
			config.save();
			return;
		}

		for( String category : config.getCategoryNames() ) // Figure out if this is a custom or dictionary ore.
		{
			String cleanStr = string.replaceAll("\\s+", "").toLowerCase(); // No whitespace or capitals in the config file categories.
			String[] splitCat = category.split("\\.");

			if( splitCat.length == 2 )
			{
				if( splitCat[0].equals( "ores" ) && splitCat[1].equals( cleanStr ) ) // Check if the current iteration is the correct category (oredict.emerald)
				{
					config.get("ores."+cleanStr, "enabled", false).set( draw );

				}
			}
		}
		config.save();
	}

	public static void updateInfo( OreInfo original, OreInfo newInfo )
	{
		for( String category : config.getCategoryNames() ) {
			String cleanStr = original.getOreName().toLowerCase();
			String[] splitCat = category.split("\\.");

			if( splitCat.length == 2 && splitCat[1].equals( cleanStr ) ) {
				String tmpCategory = "ores."+cleanStr;
				config.get(tmpCategory, "red", "").set( newInfo.color[0] );
				config.get(tmpCategory, "green", "").set( newInfo.color[1] );
				config.get(tmpCategory, "blue", "").set( newInfo.color[2] );
				config.get(tmpCategory, "name", "").set( newInfo.displayName );
				break;
			}
		}
		config.save();
	}

	public static void remove( OreInfo original ) {
		for( String category : config.getCategoryNames() ) {
			String cleanStr = original.getOreName().toLowerCase();
			String[] splitCat = category.split("\\.");
			if( splitCat.length == 2 && splitCat[1].equals( cleanStr ) ) {
				config.removeCategory( config.getCategory("ores."+cleanStr) );
				break;
			}
		}
		config.save();
	}
}
