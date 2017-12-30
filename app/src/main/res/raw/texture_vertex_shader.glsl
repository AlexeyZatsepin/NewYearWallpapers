attribute vec4 a_Position;
attribute vec4 inputTextureCoordinate;

varying vec2 textureCoordinate;

void main()
{
    gl_Position = a_Position;
    textureCoordinate = inputTextureCoordinate.xy;
}