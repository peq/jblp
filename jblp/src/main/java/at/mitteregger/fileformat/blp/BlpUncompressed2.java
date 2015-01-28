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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * A uncompressed Blp image with a color palette and a indexlist.
 *
 * @author Michael Mitteregger
 */
public class BlpUncompressed2 {

    private static final int DEFAULT_MIPMAP_INDEX = 0;

    private BlpHeader blpHeader;
    private Color[] palette = new Color[256];
    private MipMap[] mipMaps = new MipMap[16];

    private BufferedImage bufferedImage;

    /**
     * Creates a new BlpUncompressed1 instance with the BlpHeader and the InputStream with the remaining body data.
     *
     * @param blpHeader the header of the Blp.
     * @param inputStream the InputStream with the remaining body data.
     * @throws IOException if the body contains invalid data.
     */
    public BlpUncompressed2(final BlpHeader blpHeader, final InputStream inputStream) throws IOException {
        this.blpHeader = blpHeader;
        readBody(inputStream);
        createBufferedImage();
    }

    /**
     * Reads the body data.
     *
     * @param inputStream the InputStream with the remaining body data.
     * @throws IOException if the body contains invalid data.
     */
    private void readBody(final InputStream inputStream) throws IOException {
        LittleEndianDataInputStream ledis = new LittleEndianDataInputStream(inputStream);

        for (int i = 0; i < palette.length; i++) {
            palette[i] = new Color(ledis.readDWord(), false);
        }

        MipMap mipMap;
        short[] indexList;

        for (int currentMipMap = 0; currentMipMap < this.mipMaps.length; currentMipMap++) {
            mipMap = new MipMap();
            indexList = new short[this.blpHeader.getMipMapSize()[currentMipMap]];

            for (int i = 0; i < this.blpHeader.getMipMapSize()[currentMipMap]; i++) {
                indexList[i] = ledis.readUByte();
            }

            mipMap.setIndexList(indexList);
            mipMaps[currentMipMap] = mipMap;
        }
    }

    /**
     * Creates a BufferedImage using the Blp header and body.
     */
    private void createBufferedImage() {
        this.bufferedImage = new BufferedImage(this.blpHeader.getWidth(), this.blpHeader.getHeight(), //
               BufferedImage.TYPE_INT_ARGB);

        Graphics g = this.bufferedImage.createGraphics();

        int mipMapIndex = DEFAULT_MIPMAP_INDEX;
        MipMap mipMap = this.mipMaps[mipMapIndex];

        int w;
        int h;

        if (mipMapIndex == 0) {
            w = this.blpHeader.getWidth();
            h = this.blpHeader.getHeight();
        } else {
            w = this.blpHeader.getWidth() / (mipMapIndex * 2);
            h = this.blpHeader.getHeight() / (mipMapIndex * 2);
        }

        int i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color color = palette[mipMap.getIndexList()[i]];
                g.setColor(color);
                g.fillRect(x, y, 1, 1);
                i++;
            }
        }

        g.dispose();
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    /**
     * A MipMap with a index list.
     *
     * @author Michael Mitteregger
     */
    private class MipMap {

        private short[] indexList;

        public short[] getIndexList() {
            return indexList;
        }

        public void setIndexList(final short[] indexList) {
            this.indexList = indexList;
        }
    }
}
