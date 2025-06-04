#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform int u_grayscale; // 0 = رنگی، 1 = سیاه سفید

void main() {
    vec4 color = texture2D(u_texture, v_texCoords) * v_color;

    if (u_grayscale == 1) {
        float gray = dot(color.rgb, vec3(0.3, 0.59, 0.11)); // تبدیل به خاکستری
        color = vec4(vec3(gray), color.a);
    }

    gl_FragColor = color;
}
