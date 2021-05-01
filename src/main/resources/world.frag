#version 330 core

uniform sampler2D ut_diffuseAtlas;

in VS_OUT
{
    vec2 texture;
} VSO;

out vec4 fsout_color;

void main()
{
    fsout_color = vec4(1, 1, 1, 0);
}
