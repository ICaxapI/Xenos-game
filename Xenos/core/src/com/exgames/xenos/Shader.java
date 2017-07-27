package com.exgames.xenos;

/**
 * Created by Alexander on 06.07.2017.
 */
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shader {
    static final public ShaderProgram createShader() {
        String gamma = "";
        if (RayHandler.getGammaCorrection())
            gamma = "0.85 *";

        final String vertexShader =
                "attribute vec4 vertex_positions;\n" //
                        + "attribute vec4 quad_colors;\n" //
                        + "uniform mat4 u_projTrans;\n" //
                        + "void main()\n" //
                        + "{\n" //
                        + "   gl_Position =  u_projTrans * vertex_positions;\n" //
                        + "}\n";
        final String fragmentShader = "#ifdef GL_ES\n" //
                + "precision lowp float;\n" //
                + "#define MED mediump\n"
                + "#else\n"
                + "#define MED \n"
                + "#endif\n" //
                + "varying MED vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "uniform  vec4 ambient;\n"
                + "void main()\n"//
                + "{\n" //
                + "gl_FragColor.rgb = (texture2D(u_texture, v_texCoords).rgb);\n"
                + "gl_FragColor.a = 0.5;\n"
                + "}\n";
        ShaderProgram.pedantic = false;
        ShaderProgram lightShader = new ShaderProgram(vertexShader,
                fragmentShader);
        if (!lightShader.isCompiled()) {
            lightShader = new ShaderProgram("#version 330 core\n" +vertexShader,
                    "#version 330 core\n" +fragmentShader);
            if(!lightShader.isCompiled()){
                Gdx.app.log("ERROR", lightShader.getLog());
            }
        }

        return lightShader;
    }

}