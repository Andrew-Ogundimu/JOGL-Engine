package Test.Objects;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import org.joml.Matrix4f;
import Test.Textures.*;

import javax.swing.*;

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
    Test.Objects.VertexBuffer vb;
    IndexBuffer ib;
    Matrix4f proj;
    int[] indices;
    int[] dims;
    float[] positions;
    ShaderProgram prog;
    GLCanvas canvas;
    public static GL3 OpenGL;
    private Matrix4f view;
    private Matrix4f mvp;
    private Matrix4f model;

    public MainWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities cap = new GLCapabilities(profile);
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(this);
        JFrame window = new JFrame("Test");
        window.setSize(960,540);
        dims = new int[]{960,540};
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
                100.0f, 100.0f,0.0f,1.0f,
                200.0f, 100.0f,1.0f,1.0f,
                200.0f, 200.0f,1.0f,0.0f,
                100.0f, 200.0f,0.0f,0.0f};

        indices = new int[]{
                0, 1, 2,
                2, 3, 0
        };
        //System.out.println(Arrays.toString(array));
        //create vertex buffer object (vbo)
        //OpenGL.glBlendFunc()
        va = new Test.Objects.VertexArray();
        vb = new VertexBuffer(positions);

        layout = new BufferLayout();
        layout.push(GL3.GL_FLOAT,2);
        layout.push(GL3.GL_FLOAT,2);
        va.addBuffer(vb,layout);

        //create index buffer object (ibo)
        ib = new IndexBuffer(indices);
        proj = new Matrix4f().ortho(0.0f,(float)dims[0],0.0f,(float)dims[1],-1.0f,1.0f);
        view = new Matrix4f().translate(-100f,0.0f,0.0f);
        model = new Matrix4f().translate(200f,200f,0f);
        System.out.println(view);
        mvp = proj.mul(view);
        float[] projArr = new float[16];
        mvp.get(projArr);
        //OpenGL.glBindBuffer(GL3.GL_ARRAY_BUFFER,0);
        va.unbind();
        //create program
        prog = new ShaderProgram();
        //add shaders to program
        prog.addShader(new Shader("./src/Test/Shaders/testvert.glsl",GL3.GL_VERTEX_SHADER));
        prog.addShader(new Shader("./src/Test/Shaders/testfrag.glsl",GL3.GL_FRAGMENT_SHADER));
        //link, validate, and use program
        prog.linkAndValidate();
        prog.use();
        //get uniform in vertex shader
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        //set uniform to float array
        OpenGL.glUniform4fv(location,1,new float[]{0.2f,0.3f,0.8f,1.0f},0);
        OpenGL.glUniformMatrix4fv(OpenGL.glGetUniformLocation(prog.id,"u_MVP"),1,false,projArr,0);
        Texture texture = new Texture("./src/Test/Textures/alpha.png");
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
        proj = new Matrix4f().ortho(0.0f,(float)dims[0],0.0f,(float)dims[1],-1.0f,1.0f);
        view = new Matrix4f().translate(-100f,0.0f,0.0f);
        model = new Matrix4f().translate(200f,200f,0f);
        mvp = proj.mul(view).mul(model);
        float[] projArr = new float[16];
        mvp.get(projArr);
        OpenGL.glUniformMatrix4fv(OpenGL.glGetUniformLocation(prog.id,"u_MVP"),1,false,projArr,0);

        OpenGL.glClear(GL3.GL_COLOR_BUFFER_BIT);
        va.bind();
        int location = OpenGL.glGetUniformLocation(prog.id,"u_Color");
        /*if (up==true) {
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
        OpenGL.glUniform4fv(location,1,new float[]{r,0.3f,0.8f,1.0f},0);*/
        OpenGL.glDrawElements(GL3.GL_TRIANGLES,6,GL3.GL_UNSIGNED_INT,0);
        va.unbind();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        dims = new int[]{width,height};
    }
}
