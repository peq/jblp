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

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Michael Mitteregger
 */
public abstract class DrawBlpTest {
    
    protected void drawBlp(final BufferedImage image) {
        // Display JFrame with the image
        JFrame frame = new JFrame();
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Set width and height
        Insets frameInsets = frame.getInsets();
        int frameWidthInsets = frameInsets.left + frameInsets.right;
        int frameHeightInsets = frameInsets.top + frameInsets.bottom;
        frame.setSize(image.getWidth() + frameWidthInsets, image.getHeight() + frameHeightInsets);
        panel.setSize(image.getWidth(), image.getHeight());
    }
}
