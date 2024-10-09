#version 330

in vec2 a_pos;
in vec2 a_uv;
in vec4 a_color;

out vec2 f_uv;
flat out vec4 f_color;

uniform mat4 u_combined;

void main(){
    gl_Position = u_combined * vec4(a_pos, 0, 1);
    f_uv = a_uv;
    f_color = a_color;
}