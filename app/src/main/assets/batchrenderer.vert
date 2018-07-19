#version 300 es

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 uv;
layout(location = 2) in vec4 color;
layout(location = 3) in float texId;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec2 pass_uv;
out vec4 pass_color;
out float pass_texId;

void main() {
    gl_Position = vec4(position, 1.0);
    pass_uv = uv;
    pass_color = color;
    pass_texId = texId;
}