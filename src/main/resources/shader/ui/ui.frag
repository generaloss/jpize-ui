#version 330

in vec2 f_uv;
flat in vec4 f_color;

uniform sampler2D u_texture;
uniform vec2 u_center;
uniform vec2 u_size;
uniform vec4 u_corner_radius;
uniform float u_corner_softness;
uniform float u_border_width;
uniform vec4 u_border_color;
uniform float u_border_softness;

float rounded_box_sdf(vec2 center_position, vec2 size, vec4 radius){
    radius.wz = (center_position.x > 0.0) ? radius.wz : radius.xy;
    radius.w  = (center_position.y > 0.0) ? radius.w  : radius.z;

    vec2 q = abs(center_position) - size + radius.w;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.w;
}

void main() {
    vec4 color = f_color * texture2D(u_texture, f_uv);

    vec2 half_size = (u_size * 0.5); // rectangle extents (half of the size)

    float distance = rounded_box_sdf(gl_FragCoord.xy - u_center, half_size, u_corner_radius);
    float smoothed_alpha = 1.0 - smoothstep(0.0, u_corner_softness, distance); // smooth the result (free antialiasing).
    float border_alpha = 1.0 - smoothstep(u_border_width - u_border_softness, u_border_width, abs(distance) + 0.001);

    color.a = min(color.a, smoothed_alpha);
    color = mix(
        color, u_border_color,
        min(u_border_color.a, min(border_alpha, smoothed_alpha))
    );

    gl_FragColor = color;
}