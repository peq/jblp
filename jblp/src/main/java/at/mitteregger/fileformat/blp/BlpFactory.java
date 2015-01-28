/*
 * Copyright 2012 Michael Mitteregger.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.mitteregger.fileformat.blp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import at.mitteregger.fileformat.blp.enums.BlpCompression;
import at.mitteregger.fileformat.blp.enums.BlpPictureType;

/**
 * The factory to create a BufferdImage from a Blp file.
 *
 * @author Michael Mitteregger
 */
public class BlpFactory {

    /** Private constructor (Factory Pattern). */
    private BlpFactory() {
        super();
    }

    /**
     * Creates a BufferedImage from a Blp file.<br />
     * Only BLP1 files without an compression are supported yet.
     *
     * @param file the BLP file.
     * @return a BufferedImage containing the image of the Blp file.
     * @throws IOException if the file cannot be read or contains invalid data.
     * @throws UnsupportedOperationException if the file is not a BLP1 file with uncompressed data.
     */
    public static BufferedImage createBlp(final File file) throws IOException {
        return createBlp(FileUtils.openInputStream(file));
    }

    /**
     * Creates a BufferedImage from a InputSream.<br />
     * Only BLP1 files without an compression are supported yet.
     *
     * @param inputStream the InputStream of a BLP1 file.
     * @return a BufferedImage containing the image of the Blp file.
     * @throws IOException if the InputStream contains invalid data.
     * @throws UnsupportedOperationException if the InputSream does not contain a BLP1 file with uncompressed data.
     */
    public static BufferedImage createBlp(final InputStream inputStream) throws IOException {
        BlpHeader blpHeader = BlpHeader.readBlpHeader(inputStream);

        if (blpHeader.getCompression().equals(BlpCompression.UNCOMPRESSED)
               && blpHeader.getPictureType().equals(BlpPictureType.INDEX_AND_ALPHA_LIST)) {
            return new BlpUncompressed1(blpHeader, inputStream).getBufferedImage();
        } else if (blpHeader.getCompression().equals(BlpCompression.UNCOMPRESSED)
               && blpHeader.getPictureType().equals(BlpPictureType.INDEX_LIST)) {
            return new BlpUncompressed2(blpHeader, inputStream).getBufferedImage();
        } else if (blpHeader.getCompression().equals(BlpCompression.JPEG)) {
        	return new BlpJpegCompressed(blpHeader, inputStream).getBufferedImage();
        }

        throw new UnsupportedOperationException("compression: " + blpHeader.getCompression());
    }
}
