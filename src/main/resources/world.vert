#version 330 core

layout (location = 0) in vec3 l_position;
layout (location = 1) in vec3 l_normal;
layout (location = 2) in vec2 l_texture;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

out VS_OUT
{
    vec2 texture;
} VSO;

void main()
{
    gl_Position = u_projection * u_view * u_model * vec4(l_position, 1.0);
    VSO.texture = l_texture; // Для красоты на время :З
}
