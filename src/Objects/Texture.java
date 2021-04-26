package Objects;

import com.jogamp.opengl.GL3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static Objects.MainWindow.OpenGL;

public class Texture {
    private int[] rendererId;
    private String filepath;
    private ByteBuffer localBuffer;
    private int width;
    private int height;
    private int BPP;
    public Texture(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            localBuffer = ByteBuffer.wrap(((DataBufferByte)(img.getRaster().getDataBuffer())).getData());
            int num = img.getRaster().getNumDataElements();
            rendererId = new int[1];
            OpenGL.glGenTextures(1,rendererId,0);
            OpenGL.glBindTexture(GL3.GL_TEXTURE_2D,rendererId[0]);
            OpenGL.glTexParameteri(GL3.GL_TEXTURE_2D,GL3.GL_TEXTURE_MIN_FILTER,GL3.GL_LINEAR);
            OpenGL.glTexParameteri(GL3.GL_TEXTURE_2D,GL3.GL_TEXTURE_MAG_FILTER,GL3.GL_LINEAR);
            OpenGL.glTexParameteri(GL3.GL_TEXTURE_2D,GL3.GL_TEXTURE_WRAP_S,GL3.GL_CLAMP_TO_EDGE);
            OpenGL.glTexParameteri(GL3.GL_TEXTURE_2D,GL3.GL_TEXTURE_WRAP_T,GL3.GL_CLAMP_TO_EDGE);
            if (num == 3) {
                OpenGL.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGB8, img.getWidth(), img.getHeight(), 0, GL3.GL_BGR, GL3.GL_UNSIGNED_BYTE, localBuffer);
            } else if (num == 4) {
                OpenGL.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, localBuffer);
            }
            OpenGL.glBindTexture(GL3.GL_TEXTURE_2D,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalize() {
        OpenGL.glDeleteTextures(1,rendererId,0);
    }
    public void bind(int slot) {
        OpenGL.glActiveTexture(GL3.GL_TEXTURE0+slot);
        OpenGL.glBindTexture(GL3.GL_TEXTURE_2D,rendererId[0]);
    }
    public void bind() {
        OpenGL.glActiveTexture(GL3.GL_TEXTURE0);
        OpenGL.glBindTexture(GL3.GL_TEXTURE_2D,rendererId[0]);
    }

    public void unbind() {

    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
