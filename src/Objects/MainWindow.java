package Objects;

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
    int[] vao;
    float r;
    boolean up;
    Texture texture;
    VertexArray va;
    BufferLayout layout;
    VertexBuffer vb;
    IndexBuffer ib;
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
        window.setSize(640,640);
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
                -0.5f, -0.5f,0.0f,1.0f,
                0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, 1.0f,0.0f,
                -0.5f,0.5f,0.0f,0.0f};

        indices = new int[]{
                0, 1, 2,
                2, 3, 0
        };
        //System.out.println(Arrays.toString(array));
        //create vertex buffer object (vbo)
        //OpenGL.glBlendFunc()
        va = new VertexArray();
        vb = new VertexBuffer(positions);

        layout = new BufferLayout();
        layout.push(GL3.GL_FLOAT,2);
        layout.push(GL3.GL_FLOAT,2);
        va.addBuffer(vb,layout);

        //create index buffer object (ibo)
        ib = new IndexBuffer(indices);
        //OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
        va.unbind();
        //create program
        prog = new ShaderProgram();
        //add shaders to program
        prog.addShader(new Shader("./src/Shaders/testvert.glsl",GL3.GL_VERTEX_SHADER));
        prog.addShader(new Shader("./src/Shaders/testfrag.glsl",GL3.GL_FRAGMENT_SHADER));
        //link, validate, and use program
        prog.linkAndValidate();
        prog.use();
        //get uniform in vertex shader
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        //set uniform to float array
        OpenGL.glUniform4fv(location,1,new float[]{0.2f,0.3f,0.8f,1.0f},0);
        Texture texture = new Texture("./src/Textures/alpha.png");
        OpenGL.glUniform1i(OpenGL.glGetUniformLocation(prog.id,"u_Texture"),0);
        texture.bind();
        r = 0.0f;
        up = false;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        OpenGL.glClear(GL3.GL_COLOR_BUFFER_BIT);
        va.bind();
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        if (up==true) {
            r+=0.1f;
        } else {
            r-=0.1f;
        }
        if (r>1) {
            up = false;
            r-=0.1f;
        } else if (r < 0) {
            up = true;
            r+=0.1f;
        }
        OpenGL.glUniform4fv(location,1,new float[]{r,0.3f,0.8f,1.0f},0);
        OpenGL.glDrawElements(GL3.GL_TRIANGLES,6,GL3.GL_UNSIGNED_INT,0);
        va.unbind();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }
}
