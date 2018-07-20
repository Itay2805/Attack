#version 300 es

precision mediump float;
precision mediump int;

in vec2 pass_uv;
in vec4 pass_color;
in float pass_texId;

uniform sampler2D textures[16];

layout(location = 0) out vec4 color;

void main() {
	if(pass_texId < 0.5f){
		color = pass_color;
	} else {
		int id = int(pass_texId) - 1;
		if (id == 0) {
			color = texture(textures[0], pass_uv);
		} else if (id == 1) {
			color = texture(textures[1], pass_uv);
		} else if (id == 2) {
			color = texture(textures[2], pass_uv);
		} else if (id == 3) {
			color = texture(textures[3], pass_uv);
		} else if (id == 4) {
			color = texture(textures[4], pass_uv);
		} else if (id == 5) {
			color = texture(textures[5], pass_uv);
		} else if (id == 6) {
			color = texture(textures[6], pass_uv);
		} else if (id == 7) {
			color = texture(textures[7], pass_uv);
		} else if (id == 8) {
			color = texture(textures[8], pass_uv);
		} else if (id == 9) {
			color = texture(textures[9], pass_uv);
		} else if (id == 10) {
			color = texture(textures[10], pass_uv);
		} else if (id == 11) {
			color = texture(textures[11], pass_uv);
		} else if (id == 12) {
			color = texture(textures[12], pass_uv);
		} else if (id == 13) {
			color = texture(textures[13], pass_uv);
		} else if (id == 14) {
			color = texture(textures[14], pass_uv);
		} else if (id == 15) {
			color = texture(textures[15], pass_uv);
		}
	}
}