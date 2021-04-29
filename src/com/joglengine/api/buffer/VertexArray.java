package com.joglengine.api.buffer;

import com.jogamp.opengl.GL3;

import java.util.ArrayList;

import static com.joglengine.api.display.Window.OpenGL;

public class VertexArray {
    private int offset;
    private int[] vao;
    public VertexArray() {
        vao = new int[1];
        OpenGL.glGenVertexArrays(1,vao,0);
    }
    public void finalize() {
        OpenGL.glDeleteVertexArrays(1,vao,0);
    }
    void addBuffer(VertexBuffer vb, int[] layout) {
        bind();
        vb.bind();
        boolean normalized;
        if (layout[2]==0) {
            normalized = false;
        } else {
            normalized = true;
        }
        OpenGL.glBindVertexArray(vao[0]);
        OpenGL.glEnableVertexAttribArray(0);
        OpenGL.glVertexAttribPointer(0, layout[0], layout[1], normalized, 0, 0);
        OpenGL.glBindVertexArray(0);

    }
    void bind() {
        OpenGL.glBindVertexArray(vao[0]);
    }
    void unbind() {
        OpenGL.glBindVertexArray(0);
    }
}