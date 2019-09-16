# Programmierprojekt

### help

Lists all available commands, their arguments and help messages.
Note: In the later examples the name of the program file will be
shortened to cc (color converter).

No arguments required

No examples found


### list

Displays all colors of a file

Arguments:

- filepath: The path to the file which should be read

Examples:

```bash
cc list "path/to/palette.ext"
```


### convertFile

Converts a file into another fileformat.
Both input- and export-fileformats must be specified because some
extensions can store different formats, e.g. .xml can be used by
MS Office, Libre Office etc.

Arguments:

- inputPath: The path to the input file
- outputPath: The path to the output file
- inputFormat: The fileformat of the input file
- outputFormat: The fileformat of the output file

Examples:

```bash
cc convertFile gimp "path/to/gimpPalette.xml" libreoffice "path/to/output.xml"
```


### convertColor

Converts a single color into the specified format.
Available formats are: hex, rgb, cmyk.

Arguments:

- color: The color which should be converted
- inputFormat: The format of the specified color
- outputFormat: The format of the converted color

Examples:

```bash
cc convertColor hex #123456 rgb
cc convertColor hex #123456 cmyk
```


### demo

demo command, for testing stuff while developing

No arguments required

No examples found