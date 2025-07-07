package guimodule;

import processing.core.PApplet;

public class SmileyFace extends PApplet {
	
	public void setup() {
		size(400, 400);
		background(200, 200, 200);
	}
	public void draw() {
		// face
		fill(255, 255, 0);
		int center_w = width/2;
		int center_h = height/2;
		ellipse(center_w, center_h, width-10, height-10);
		
		//eyes
		fill(0,0,0);
		int pos_w = width/3;
		int pos_h = height/3;
		int len_w = width/10;
		int len_h = height/6;
		ellipse(pos_w, pos_h, len_w, len_h);
		ellipse(width -pos_w, pos_h, len_w, len_h);
		
		// smile
		noFill();
		arc(center_w, height-height/3, pos_w, pos_w, 0, PI);
	}
}
