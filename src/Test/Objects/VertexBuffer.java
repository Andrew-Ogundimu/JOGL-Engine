package Test.Objects;

import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;

public class VertexBuffer {
    private int[] RendererID;


    public VertexBuffer(float[] data) {
        RendererID = new int[1];
        MainWindow.OpenGL.glGenBuffers(1,RendererID,0);
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,RendererID[0]);
        //add position data to vbo
        MainWindow.OpenGL.glBufferData(GL3.GL_ARRAY_BUFFER,Float.BYTES*data.length, FloatBuffer.wrap(data),GL3.GL_STATIC_DRAW);
    }
    public void finalize() {
        MainWindow.OpenGL.glDeleteBuffers(1,RendererID,0);
    }
    public void bind() {
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,RendererID[0]);
    }
    public void unbind() {
        MainWindow.OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
    }
}
