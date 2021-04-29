package com.joglengine.api.object;

import com.joglengine.api.buffer.IndexBuffer;
import com.joglengine.api.buffer.VertexArray;
import com.joglengine.api.buffer.VertexBuffer;
import com.joglengine.api.texture.Texture;

public class Sprite {
    private VertexBuffer vbo;
    private VertexArray vao;
    private IndexBuffer ibo;
    private double[] position = new double[]{0,0};

    public void setShape(Shape s) {
        vbo = s.getVbo();
        vbo.unbind();
        int[] iboArr = new int[(vbo.data.length-2)*3];
        for (int i=0; i<iboArr.length; i++) {
            iboArr[i*3] = 0;
            iboArr[i*3+1] = i+1;
            iboArr[i*3+2] = i+2;
        }
        ibo = new IndexBuffer(iboArr);
    }
    public void init() {

    }
    public void bind() {

    }
    public double[] getPos() {
        return position;
    }
    public void setPos(double x, double y) {
        position = new double[]{x,y};
    }
    public void setTexture(Texture tex) {

    }
}
