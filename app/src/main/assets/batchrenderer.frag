#version 300 es

in vec2 pass_uv;
in vec4 pass_color;
in float pass_texId;

uniform sampler2D textures[32];

out vec4 color;

void main() {
	color = pass_color;
}