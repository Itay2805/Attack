#version 300 es

in vec2 uvs;

uniform sampler2D tex;

out vec4 color;

void main() {
    color = texture(tex, uvs);
}