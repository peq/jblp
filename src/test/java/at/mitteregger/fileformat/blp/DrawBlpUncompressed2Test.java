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

/**
 *
 * @author Michael Mitteregger
 */
public class DrawBlpUncompressed2Test extends DrawBlpTest {

    public static void main(final String[] args) throws IOException {
        final File blp1File = new File("src/test/resources/files/palm.blp");
        final BufferedImage image = BlpFactory.createBlp(blp1File);

        new DrawBlpUncompressed2Test().drawBlp(image);
    }
}
