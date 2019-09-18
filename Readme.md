# Programmierprojekt

[Link to JavaDoc](https://syndesi.github.io/color-palette-converter/)

## How to use

The generated library `dist/cc.jar` can be started in two ways:

### Direct way

In this mode the program is started with all required parameters and will execute them without further questions.

```bash
java -jar dist/cc.jar help
#...
```

### Interactive way

In this mode the program is started without any parameters at all. It will then ask for its parameters or will print the programs help-text.

```bash
java -jar dist/cc.jar
#No arguments specified.
#Please enter command manually: help
#...
```

## Example files

Example files can be found in `src/assets`. Additional files are usually included in the following folders:

- `C:\Program Files\LibreOffice\share\palette` (only `*.soc` can be interpreted)
- `C:\Program Files\GIMP 2\share\gimp\2.0\palettes`
- `C:\Program Files\Adobe\Adobe Photoshop CC 2019\Presets\Color Swatches` (`*.aco` files need to be converted into `*.ase` inside Adobe Photoshop)

## Dependencies

This project uses multiple dependencies:

- [Apache's Commons Lang](https://commons.apache.org/proper/commons-lang/) for [string](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html) and [array](https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/ArrayUtils.html) functions
- [Apache's Commons Text](https://commons.apache.org/proper/commons-text/) for [text formatting functions](https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/WordUtils.html)
- [Catalano Framework](https://github.com/DiegoCatalano/Catalano-Framework) for its [color converter](https://github.com/DiegoCatalano/Catalano-Framework/blob/master/Catalano.Image/src/Catalano/Imaging/Tools/ColorConverter.java)