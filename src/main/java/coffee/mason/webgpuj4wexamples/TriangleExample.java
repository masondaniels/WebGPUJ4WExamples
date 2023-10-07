package coffee.mason.webgpuj4wexamples;

import coffee.mason.webgpuj4w.canvas.Canvas2D;
import coffee.mason.webgpuj4w.canvas.WebGPUCanvas;
import coffee.mason.webgpuj4w.javascript.WebGPUCommandEncoder;
import coffee.mason.webgpuj4w.javascript.WebGPUContext;
import coffee.mason.webgpuj4w.javascript.WebGPUInstance;
import coffee.mason.webgpuj4w.javascript.WebGPUPassEncoder;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUBuffer;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUBufferType;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUBufferUsage;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUConfiguration;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUShader;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUShaderType;
import coffee.mason.webgpuj4w.javascript.wrapper.WebGPUVisibility;


public class TriangleExample {
	
	private static String vertexCode = "@vertex\r\n" + 
			"                fn main(\r\n" + 
			"                @builtin(vertex_index) VertexIndex : u32\r\n" + 
			"                ) -> @builtin(position) vec4<f32> {\r\n" + 
			"                var pos = array<vec2<f32>, 3>(\r\n" + 
			"                    vec2(0.0, 0.5),\r\n" + 
			"                    vec2(-0.5, -0.5),\r\n" + 
			"                    vec2(0.5, -0.5)\r\n" + 
			"                );\r\n" + 
			"\r\n" + 
			"                return vec4<f32>(pos[VertexIndex], 0.0, 1.0);\r\n" + 
			"                }";
	
	private static String fragmentCode = "struct Uniform {\r\n" + 
			"                    time: f32, // in seconds\r\n" + 
			"                };\r\n" + 
			"\r\n" + 
			"                @group(0) @binding(0)\r\n" + 
			"                var<uniform> uniforms: Uniform;\r\n" + 
			"\r\n" + 
			"                @fragment\r\n" + 
			"                fn main() -> @location(0) vec4<f32> {\r\n" + 
			"                return vec4(pow(sin(uniforms.time),2), pow(cos(uniforms.time),1.3), pow(cos(uniforms.time),2.4), 1.0);\r\n" + 
			"                }";
	
	
	public static void main(String[] args) {
		// Create a webgpu instance, which will be used to draw to a webgpu canvas.
		final WebGPUInstance webgpu = new WebGPUInstance(new WebGPUConfiguration() {
			
			@Override
			public WebGPUShader[] getShaders() {
				WebGPUShader vertex = new WebGPUShader(WebGPUShaderType.VERTEX, vertexCode);
				WebGPUShader fragment = new WebGPUShader(WebGPUShaderType.FRAGMENT, fragmentCode);
				
				WebGPUShader[] shaders = {vertex, fragment};
				return shaders;
			}
			
			@Override
			public WebGPUBuffer[] getBuffers() {
				WebGPUVisibility[] visibility = {WebGPUVisibility.FRAGMENT};
				WebGPUBufferUsage[] usages = {WebGPUBufferUsage.UNIFORM, WebGPUBufferUsage.COPY_DST};
				// The name "spongebob" is the internal (java) name of the javascript buffer object. It is used when writing values to the buffer.
				WebGPUBuffer uniform = new WebGPUBuffer("spongebob", 0, 4, WebGPUBufferType.UNIFORM, visibility, usages);
				WebGPUBuffer[] buffers = {uniform};
				return buffers;
			}
		});
		
		// 2D UI which draws "hello"
		Canvas2D ui = new Canvas2D(true) {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadBeforeAnimation() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadAfterAnimation() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void draw() {
				getCtx().setFillStyle("#FFFFFF");
				getCtx().fillText("Hello!", 10, 20);
			}
		};
		
		// Set z index to 2 so it displays above webgpu canvas
		ui.setZIndex(2);
		
		// Create WebGPU Canvas which will be used to draw
		WebGPUCanvas wc = new WebGPUCanvas(true, webgpu) {
			
			private long init = System.currentTimeMillis();
			private long last = System.currentTimeMillis();
			private int counter;
			private int counterFps;
			
			@Override
			public void update() {
				// FPS LOGIC
				counter++;
				if (System.currentTimeMillis() - last > 1000L) {
					counterFps = counter;
					counter = 0;
					last = System.currentTimeMillis();
				}
			}
			
			@Override
			public void loadBeforeAnimation() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void loadAfterAnimation() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void draw() {
				WebGPUContext ctx = getCtx();
				WebGPUCommandEncoder encoder = webgpu.createCommandEncoder(ctx);
				WebGPUPassEncoder passEncoder = encoder.beginRenderPass();
				passEncoder.setup(webgpu);
				passEncoder.draw(3);
				passEncoder.end();
				// Write f32 time at position 0 using the buffer name "spongebob" defined in webgpu instance config
				encoder.writeFloat("spongebob", (float)((System.currentTimeMillis()-init)*0.001), 0);
				// If you wanted to write another float, you'd have to update the uniform buffer "spongebob" size (from 4 to 8)
				// as well as write the float here like so:
				// encoder.writeFloat("spongebob", (float) float, 4);
				encoder.submit();
			}
			

			
			@Override
			public void onResize() {
				// TODO Auto-generated method stub
			}
		};
		
		// Set z index to 1 so it is drawn below ui
		wc.setZIndex(1);

	}

}
