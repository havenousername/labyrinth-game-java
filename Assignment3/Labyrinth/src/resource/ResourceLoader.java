/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author andreicristea
 */
public class ResourceLoader {
    public static InputStream loadResource(String resourceName) {
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName);
    }
    
    public static Image loadImage(String resourceName) throws IOException {
        URL url = ResourceLoader.class.getClassLoader().getResource(resourceName);
        return ImageIO.read(url);
    }
}
