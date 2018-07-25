#version 300 es

precision mediump float;
precision mediump int;

in vec2 pass_uv;
in vec4 pass_color;
in float pass_texId;

uniform sampler2D textures[16];

layout(location = 0) out vec4 color;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

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
		}else {
			discard;
		}

		if(pass_color.w > 0.1 && color.a != 0.0) {
			vec3 hsv = rgb2hsv(pass_color.xyz);
			hsv.z *= dot(color.rgb, vec3(0.299, 0.587, 0.114));
			color = vec4(hsv2rgb(hsv), pass_color.a);
		}
	}
}