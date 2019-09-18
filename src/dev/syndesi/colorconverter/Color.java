package dev.syndesi.colorconverter;


/**
 * Generic class for saving colors with its title.
 * @author Syndesi
 * @since 1.0
 */
public class Color {

	protected byte red;
	protected byte green;
	protected byte blue;
	protected byte alpha;
	protected String title;
	
	/**
	 * Creates a new color by using red, green, blue and alpha.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 * @param alpha the alpha channel
	 */
	public Color (byte red, byte green, byte blue, byte alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		this.title = "";
	}
	
	/**
	 * Creates a new color by using red, green, blue and alpha.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 * @param alpha the alpha channel
	 */
	public Color (byte red, byte green, byte blue, float alpha) {
		this(red, green, blue, (byte) (255 * alpha));
	}

	/**
	 * Creates a new color just by using red, green and blue. The alpha-channel will be set to 100%.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 */
	public Color (byte red, byte green, byte blue) {
		this(red, green, blue, (byte) 255);
	}
	
	/**
	 * Creates a new color by using red, green, blue and alpha.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 * @param alpha the alpha channel
	 */
	public Color (int red, int green, int blue, int alpha) {
		this((byte) red, (byte) green, (byte) blue, (byte) alpha);
	}

	/**
	 * Creates a new color by using red, green, blue and alpha.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 * @param alpha the alpha channel
	 */
	public Color (int red, int green, int blue, float alpha) {
		this((byte) red, (byte) green, (byte) blue, (float) alpha);
	}
	
	/**
	 * Creates a new color just by using red, green and blue. The alpha-channel will be set to 100%.
	 * @param red the red channel
	 * @param green the green channel
	 * @param blue the blue channel
	 */
	public Color (int red, int green, int blue) {
		this((byte) red, (byte) green, (byte) blue);
	}
	
	/**
	 * Sets this color to white (#FFFFFF)
	 */
	public Color () {
		this(255, 255, 255);
	}
	
	/**
	 * @return the red channel
	 */
	public byte getRed () {
		return this.red;
	}
	
	/**
	 * @param red the red channel
	 */
	public void setRed (byte red) {
		this.red = red;
	}
	
	/**
	 * @param red the red channel
	 */
	public void setRed (int red) {
		this.red = (byte) red;
	}
	
	/**
	 * @return the green channel
	 */
	public byte getGreen () {
		return this.green;
	}
	
	/**
	 * @param green the green channel
	 */
	public void setGreen (byte green) {
		this.green = green;
	}
	
	/**
	 * @param green the green channel
	 */
	public void setGreen (int green) {
		this.green = (byte) green;
	}
	
	/**
	 * @return the blue channel
	 */
	public byte getBlue () {
		return this.blue;
	}
	
	/**
	 * @param blue the blue channel
	 */
	public void setBlue (byte blue) {
		this.blue = blue;
	}
	
	/**
	 * @param blue the blue channel
	 */
	public void setBlue (int blue) {
		this.blue = (byte) blue;
	}
	
	/**
	 * @return the alpha channel
	 */
	public byte getAlpha () {
		return this.alpha;
	}
	
	/**
	 * @param alpha the alpha channel
	 */
	public void setAlpha (byte alpha) {
		this.alpha = alpha;
	}
	
	/**
	 * @param alpha the alpha channel
	 */
	public void setAlpha (int alpha) {
		this.alpha = (byte) alpha;
	}
	
	/**
	 * @param alpha the alpha channel
	 */
	public void setAlpha (float alpha) {
		this.alpha = (byte) (255 * alpha);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle () {
		return this.title;
	}
	
	/**
	 * @param title the title of the color
	 */
	public void setTitle (String title) {
		this.title = title;
	}
	
}
