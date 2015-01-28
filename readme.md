# About

This is a fork of [jblp](https://code.google.com/p/jblp/) which tries to add JPEG supprt.

# Short description

A Java library to read BLP files and convert them to BufferedImages.
Usage

	File blpFile = new File("path/to/your/blp");
	BufferedImage img = BlpFactory.createBlp(blpFile);

# The Good and the Bad

- 2 Lines of code
- Also takes InputStream as argument 
- Only works with uncompressed BLP1 images for now. JPEG support is experimental.
