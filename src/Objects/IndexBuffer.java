package Objects;

import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class IndexBuffer {
    private int[] RendererID;


    public IndexBuffer(int[] data) {
        RendererID = new int[1];
        MainWindow.OpenGL.glGenBuffers(1,RendererID,0);
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,RendererID[0]);
        //add position data to vbo
        MainWindow.OpenGL.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER,Integer.BYTES*data.length, IntBuffer.wrap(data),GL3.GL_STATIC_DRAW);
    }
    public void finalize() {
        MainWindow.OpenGL.glDeleteBuffers(1,RendererID,0);
    }
    public void bind() {
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,RendererID[0]);
    }
    public void unbind() {
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,0);
    }
}
