package com.joglengine.api.buffer;
import com.jogamp.opengl.GL3;

import java.nio.DoubleBuffer;

import static com.joglengine.api.display.Window.OpenGL;

public class VertexBuffer {
    int id;
    public double[] data;
    public VertexBuffer(double[] data) {
        this.data = data;
        int[] vboBuff = new int[1];
        OpenGL.glGenBuffers(1,vboBuff,0);
        id = vboBuff[0];
        OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,id);
        OpenGL.glBufferData(GL3.GL_ARRAY_BUFFER,Double.BYTES*data.length, DoubleBuffer.wrap(data),GL3.GL_STATIC_DRAW);
        OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
    }
    public void finalize() {
        OpenGL.glDeleteBuffers(1,new int[]{id},0);
    }
    public void bind() {
        OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,id);
    }
    public void unbind() {
        OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
    }
}
