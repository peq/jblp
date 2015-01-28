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

import java.io.InputStream;

import at.mitteregger.fileformat.blp.enums.BlpCompression;
import at.mitteregger.fileformat.blp.enums.BlpPictureType;
import at.mitteregger.fileformat.blp.enums.BlpType;
import java.io.IOException;

/**
 * A BLP1 header.
 *
 * @author Michael Mitteregger
 */
public class BlpHeader {

    /** The number of bytes read from the InputStream to get the header data. */
    public static final int HEADER_SIZE = 1;

    /** The length of the Blp type String. */
    private static final int BLP_TYPE_LENGTH = 4;

    /** The number of MipMap offsets. */
    private static final int MIPMAP_OFFSET_LENGTH = 16;

    /** The number of MipMap sizes. */
    private static final int MIPMAP_SIZE_LENGTH = 16;

    private BlpType type;
    private BlpCompression compression;
    private int alphaDepth;
    private int width;
    private int height;
    private BlpPictureType pictureType;
    private int pictureSubType;
    private int[] mipMapOffset;
    private int[] mipMapSize;

    /** Private constructor (Factory Pattern). */
    private BlpHeader() {
        super();
    }

    /**
     * Reads and returns a Blp header from the given InputStream.
     *
     * @param inputStream the InputStream with the Blp header data.
     * @return the BlpHeader object.
     * @throws IOException if the header contains invalid data.
     * @throws UnsupportedOperationException if the Blp format is not supported yet.
     */
    public static BlpHeader readBlpHeader(final InputStream inputStream) throws IOException {
        LittleEndianDataInputStream ledis = new LittleEndianDataInputStream(inputStream);

        BlpHeader blpHeader = new BlpHeader();
        blpHeader.setType(BlpType.valueOf(ledis.readString(BLP_TYPE_LENGTH)));

        if (!blpHeader.getType().equals(BlpType.BLP1)) {
            throw new UnsupportedOperationException("BLP type " + blpHeader.getType() + " is not supported yet.");
        }

        blpHeader.setCompression(BlpCompression.byBlpCompressionId(ledis.readDWord()));
        blpHeader.setAlphaDepth(ledis.readDWord());
        blpHeader.setWidth(ledis.readDWord());
        blpHeader.setHeight(ledis.readDWord());
        blpHeader.setPictureType(BlpPictureType.byBlpPictureTypeId(ledis.readDWord()));
        blpHeader.setPictureSubType(ledis.readDWord());

        int[] mipMapOffset = new int[MIPMAP_OFFSET_LENGTH];
        for (int i = 0; i < mipMapOffset.length; i++) {
            mipMapOffset[i] = ledis.readDWord();
        }
        blpHeader.setMipMapOffset(mipMapOffset);

        int[] mipMapSize = new int[MIPMAP_SIZE_LENGTH];
        for (int i = 0; i < mipMapSize.length; i++) {
            mipMapSize[i] = ledis.readDWord();
        }
        blpHeader.setMipMapSize(mipMapSize);

        return blpHeader;
    }

    public BlpType getType() {
        return type;
    }

    public void setType(final BlpType type) {
        this.type = type;
    }

    public BlpCompression getCompression() {
        return compression;
    }

    public void setCompression(final BlpCompression compression) {
        this.compression = compression;
    }

    public int getAlphaDepth() {
        return alphaDepth;
    }

    public void setAlphaDepth(final int alphaDepth) {
        this.alphaDepth = alphaDepth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public BlpPictureType getPictureType() {
        return pictureType;
    }

    public void setPictureType(final BlpPictureType pictureType) {
        this.pictureType = pictureType;
    }

    public int getPictureSubType() {
        return pictureSubType;
    }

    public void setPictureSubType(final int pictureSubType) {
        this.pictureSubType = pictureSubType;
    }

    public int[] getMipMapOffset() {
        return mipMapOffset;
    }

    public void setMipMapOffset(final int[] mipMapOffset) {
        this.mipMapOffset = mipMapOffset;
    }

    public int[] getMipMapSize() {
        return mipMapSize;
    }

    public void setMipMapSize(final int[] mipMapSize) {
        this.mipMapSize = mipMapSize;
    }
}
