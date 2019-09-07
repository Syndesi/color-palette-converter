package dev.syndesi.colorconverter;

public class Color {

	protected byte red;
	protected byte green;
	protected byte blue;
	protected byte alpha;
	protected String title;
	
	public Color (byte red, byte green, byte blue, byte alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		this.title = "";
	}
	
	public Color (byte red, byte green, byte blue, float alpha) {
		this(red, green, blue, (byte) (255 * alpha));
	}
	
	public Color (byte red, byte green, byte blue) {
		this(red, green, blue, (byte) 255);
	}
	
	public Color (int red, int green, int blue, int alpha) {
		this((byte) red, (byte) green, (byte) blue, (byte) alpha);
	}
	
	public Color (int red, int green, int blue, float alpha) {
		this((byte) red, (byte) green, (byte) blue, (float) alpha);
	}
	
	public Color (int red, int green, int blue) {
		this((byte) red, (byte) green, (byte) blue);
	}
	
	public Color () {
		this(255, 255, 255);
	}
	
	public byte getRed () {
		return this.red;
	}
	
	public void setRed (byte red) {
		this.red = red;
	}
	
	public void setRed (int red) {
		this.red = (byte) red;
	}
	
	public byte getGreen () {
		return this.green;
	}
	
	public void setGreen (byte green) {
		this.green = green;
	}
	
	public void setGreen (int green) {
		this.green = (byte) green;
	}
	
	public byte getBlue () {
		return this.blue;
	}
	
	public void setBlue (byte blue) {
		this.blue = blue;
	}
	
	public void setBlue (int blue) {
		this.blue = (byte) blue;
	}
	
	public byte getAlpha () {
		return this.alpha;
	}
	
	public void setAlpha (byte alpha) {
		this.alpha = alpha;
	}
	
	public void setAlpha (int alpha) {
		this.alpha = (byte) alpha;
	}
	
	public void setAlpha (float alpha) {
		this.alpha = (byte) (255 * alpha);
	}
	
	public String getTitle () {
		return this.title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
}
