package net.purgo.customcat.core;

import static playn.core.PlayN.*;
import playn.core.*;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.Touch.Event;

public class CustomCat extends Game.Default {

	public CustomCat() {
		super(33); // call update every 33ms (30 times per second)
	}

	static final float GRAVITY = 64;

	ImageLayer catLayer;

	float px, py;
	float x, y;
	float vx, vy;
	float ax, ay;
	private boolean hold;
	
	float lastMouseX, lastMouseY;

	private CanvasImage img;
	
	ImageLayer drawingLayer;

	@Override
	public void init() {
		// create and add background image layer
		Image bgImage = assets().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
//		img = graphics().createSurface(100, 100);
		img = graphics().createImage(100, 100);
		drawingLayer = graphics().createImageLayer();
		graphics().rootLayer().add(drawingLayer);	
		drawingLayer.setImage(img);

		Image cat = assets().getImage("images/cat.jpg");
		catLayer = graphics().createImageLayer(cat);
		graphics().rootLayer().add(catLayer);
		
		catLayer.addListener(new Mouse.LayerListener() {
			
			// The position on the layer where the click occurred
			float relX, relY;
			
			/** The last time we clicked a drag event */ 
			private double lastTime;
			
			@Override
			public void onMouseWheelScroll(WheelEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseUp(ButtonEvent event) {
				hold = false;
			}
			
			@Override
			public void onMouseOver(MotionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseOut(MotionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseMove(MotionEvent event) {
			}
			
			@Override
			public void onMouseDrag(MotionEvent event) {
				double deltaT = (event.time() - lastTime)/1000;
				float deltaX = event.x() - relX - x;
				float deltaY = event.y() - relY - y;
				if(deltaT > 0) {
					vx = (float) (deltaX / deltaT);
					vy = (float) (deltaY / deltaT);
				}
				System.out.println("dx " + deltaX + " dt=" + deltaT + " vx=" + vx);
				x = event.x() - relX;
				y = event.y() - relY;
				lastTime = event.time();
				
			}
			
			@Override
			public void onMouseDown(ButtonEvent event) {
				vx = vy = 0;
				relX = event.localX();
				relY = event.localY();
				hold = true;
				lastTime = event.time();
				
			}
		});
		
		catLayer.addListener(new Touch.LayerListener() {
			

			@Override
			public void onTouchStart(Event touch) {
				vx = vy = 0;
				hold = true;
			}
			
			@Override
			public void onTouchMove(Event touch) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTouchEnd(Event touch) {
				hold = false;
			}
			
			@Override
			public void onTouchCancel(Event touch) {
				// TODO Auto-generated method stub
				
			}
		});

//		x = graphics().width() / 2;
		y = graphics().height() / 2;
		ay = GRAVITY;
	}

	@Override
	public void update(int d) {
		// d is in milliseconds
		// delta is in seconds
		
		// Save previous position for interpolation.
		px = x;
		py = y;

		float delta = d/1000f;
		// Update physics.
		if(!hold) {
			vx += ax * delta;
			vy += ay * delta;
			x += vx * delta;
			y += vy * delta;
		}
		
		if(y+32 > graphics().height() || y < 0)
			vy = -vy;
		
		if(x+32 > graphics().width() || x < 0)
			vx = -vx;

	}

	int c[] = new int[3];
	int i = 0;
	
	public void paint(float alpha) {
		// Interpolate current position.
		float x = (this.x * alpha) + (px * (1f - alpha));
		float y = (this.y * alpha) + (py * (1f - alpha));
//		System.out.println("hold="+hold +" alpha=" + alpha + " x=" + x + " y=" + y + " vx = " + vx + " vy= " + vy);

		catLayer.setTranslation(x, y);
		
		Path path = img.canvas().createPath();
		path.moveTo(20,20);
		path.lineTo(20, 70);
		path.lineTo(70, 70);
		path.lineTo(70, 20);
		path.close();

		
		img.canvas().setFillColor(Color.rgb(c[0], c[1], c[2]));
		c[i]++;
		if(c[i] > 255) {
			c[i] = 0;
			i++;
			if(i > 2)
				i = 0;
		}
		
		
		img.canvas().setStrokeColor(Color.rgb(128, 0, 128));
		img.canvas().strokePath(path);
		img.canvas().clip(path);

		img.canvas().fillRect(5,  5,  50, 50);

		
		// Update the layer.
//		layer.resetTransform();
//		layer.setTranslation(x - layer.image().width() / 2, y
//				- layer.image().height() / 2);
	}

	public int updateRate() {
		return 12;
	}
}
