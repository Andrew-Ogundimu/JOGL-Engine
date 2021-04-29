package com.joglengine.api.buffer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

import static com.joglengine.api.display.Window.OpenGL;

public class IndexBuffer {
    int id;
    public IndexBuffer(int[] data) {
        int[] vboBuff = new int[1];
        OpenGL.glGenBuffers(1,vboBuff,0);
        id = vboBuff[0];
        OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,id);
        //add position data to vbo
        OpenGL.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER,Integer.BYTES*data.length, IntBuffer.wrap(data),GL3.GL_STATIC_DRAW);
    }
    public void finalize() {
        OpenGL.glDeleteBuffers(1,new int[]{id},0);
    }
    public void bind() {
        OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,id);
    }
    public void unbind() {
        OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,0);
    }
}
