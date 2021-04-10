import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Random;

public class MainWindow implements GLEventListener {
    FPSAnimator fps;
    int[] buffer;
    int[] ibo;
    int[] array;
    int[] indices;
    float[] positions;
    ShaderProgram prog;
    GLCanvas canvas;
    static GL3 OpenGL;
    public MainWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities cap = new GLCapabilities(profile);
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(this);
        JFrame window = new JFrame("Test");
        window.setSize(1280,720);
        window.add(canvas);
        window.setVisible(true);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        fps = new FPSAnimator(canvas,60);
        fps.start();
    }
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        OpenGL = glAutoDrawable.getGL().getGL3();
        System.out.println(OpenGL.glGetString(GL3.GL_VERSION));

        positions = new float[]{
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f,0.5f};

        indices = new int[]{
                0, 1, 2,
                2, 3, 0
        };
        //System.out.println(Arrays.toString(array));
        buffer = new int[1];
        OpenGL.glGenBuffers(1,buffer,0);
        OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,buffer[0]);
        OpenGL.glBufferData(GL3.GL_ARRAY_BUFFER,Float.BYTES*positions.length,FloatBuffer.wrap(positions),GL3.GL_STATIC_DRAW);

        OpenGL.glVertexAttribPointer(0,2,GL3.GL_FLOAT,false,Float.BYTES*2,0);
        OpenGL.glEnableVertexAttribArray(0);

        ibo = new int[1];
        OpenGL.glGenBuffers(1,ibo,0);
        OpenGL.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER,ibo[0]);
        OpenGL.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER,Integer.BYTES*indices.length, IntBuffer.wrap(indices),GL3.GL_STATIC_DRAW);
        //OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
        prog = new ShaderProgram();
        prog.addShader(new Shader("./src/Shaders/testvert.glsl",GL3.GL_VERTEX_SHADER));
        prog.addShader(new Shader("./src/Shaders/testfrag.glsl",GL3.GL_FRAGMENT_SHADER));
        prog.linkAndValidate();
        prog.use();
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        OpenGL.glUniform4fv(location,1,new float[]{0.2f,0.3f,0.8f,1.0f},0);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        OpenGL.glClear(GL3.GL_COLOR_BUFFER_BIT);
        //OpenGL.glBindVertexArray(array[0]);
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        OpenGL.glDrawElements(GL3.GL_TRIANGLES,6,GL3.GL_UNSIGNED_INT,0);
        //OpenGL.glBindVertexArray(0);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
