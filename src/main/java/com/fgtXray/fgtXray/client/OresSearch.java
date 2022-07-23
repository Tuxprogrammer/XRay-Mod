package com.fgtXray.fgtXray.client;

import com.fgtXray.fgtXray.FgtXRay;
import com.fgtXray.fgtXray.config.ConfigHandler;
import com.fgtXray.fgtXray.reference.OreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class OresSearch
{
	private static Minecraft mc = Minecraft.getMinecraft();

	public static void add( int oreId, int oreMeta, String name, int[] color ) // Takes a string of id:meta or oreName to add to our search list.
	{
		if( name.equals("") || name.toLowerCase().equals( "gui name" ) || name.isEmpty() ) {
			mc.ingameGUI.getChatGUI().printChatMessage( new ChatComponentText( "[XRay] You need to have all the inputs filled" ));
			return;
		}

		for( OreInfo info : FgtXRay.searchList ) {
			if( info.getId() == oreId && info.getMeta() == oreMeta) {
				mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[XRay] This block has already been added to the block list"));
				return;
			}
		}

		FgtXRay.searchList.add( new OreInfo( name, name.replaceAll("\\s+", ""), oreId, oreMeta, color, true ) );
		String notify = String.format( "[XRay] successfully added %s.", name );
		mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(notify));

		ConfigHandler.add(name, oreId, oreMeta, color);
	}

	public static void update( OreInfo original, String name, int[] color ) {
		if( !FgtXRay.searchList.contains( original ) ) {
			// This really shouldn't happen but hay, lets support it anyway.
			mc.ingameGUI.getChatGUI().printChatMessage( new ChatComponentText( "[XRay] Looks like the Ore you've tried to edit does not exist?" ));
			return;
		}

		OreInfo preserve = new OreInfo( original.getDisplayName(), original.getOreName(), original.getId(), original.getMeta(), original.color, original.draw );

		OreInfo tmpNew = null;
		for ( OreInfo ore : FgtXRay.searchList ) {
			if( ore == original ) {
				ore.displayName = name;
				ore.color = color;
				tmpNew = ore;
				break;
			}
		}

		if( tmpNew != null ) {
			ConfigHandler.updateInfo(preserve, tmpNew);
			String notify = String.format( "[XRay] successfully updated %s.", preserve.getOreName() );
			mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(notify));
		} else {
			mc.ingameGUI.getChatGUI().printChatMessage( new ChatComponentText( "[XRay] Looks like the Ore you've tried to edit does not exist?" ));
		}
	}

	public static void remove( OreInfo original ) {
		if( !FgtXRay.searchList.contains( original ) ) {
			// This really shouldn't happen but hay, lets support it anyway.
			mc.ingameGUI.getChatGUI().printChatMessage( new ChatComponentText( "[XRay] Looks like the Ore you've tried to edit does not exist?" ));
			return;
		}

		FgtXRay.searchList.remove( original );
		ConfigHandler.remove(original);

		String notify = String.format( "[XRay] successfully removed %s.", original.getOreName() );
		mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(notify));
	}
}