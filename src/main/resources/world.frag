#version 330 core

uniform sampler2D ut_diffuseAtlas;

in VS_OUT
{
    vec3 texture;
} VSO;

out vec4 fsout_color;

void main()
{
    fsout_color = vec4(VSO.texture, 0);
}
