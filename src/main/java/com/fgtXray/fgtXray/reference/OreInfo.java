package com.fgtXray.fgtXray.reference;

public class OreInfo
{
	public int id;         // Id of this block
	public String oreName;
	public int meta;       // Metadata value of this block. 0 otherwise.
	public int[] color;	   // Color in 0xRRGGBB to draw.
	public boolean draw;   // Should we draw this ore?
	public String displayName;

	public OreInfo( String name, int[] color, boolean draw ) {
		this.oreName = name;
		this.displayName = "";
		this.id = 0;
		this.meta = 0;
		this.color = color;
		this.draw = draw;
	}

	public OreInfo( String displayName,  String name, int id, int meta, int[] color, boolean draw )
	{
		this.oreName = name;
		this.displayName = displayName;
		this.id = id;
		this.meta = meta;
		this.color = color;
		this.draw = draw;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getOreName() {
		return oreName;
	}

	public int getMeta() {
		return meta;
	}

	public int getId() {
		return id;
	}

	public void disable() // Stop drawing this ore.
	{
		this.draw = false;
	}
	
	public void enable()  // Start drawing this ore.
	{
		this.draw = true;
	}
}