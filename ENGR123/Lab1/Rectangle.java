// Construct a rectangle from two arbitrary points
public class Rectangle {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    public Rectangle(int x1, int y1, int x2, int y2) {
	this.x1 = x1;
	this.y1 = y1;
	this.x2 = x2;
	this.y2 = y2;
    }
    
    public boolean contains(int x, int y) {
	if(x1 <= x2) {
	    if((x1 <= x && x <= x2) || (x2 <= x && x <= x1)){
		if(y1 <= y2) {
		    return y1 <= y && y <= y2;
		} else {
		    return y2 <= y && y <= y1;
		}
	    }
	}
	return false;
    }

    public boolean InOrder(int n1, int n2, int n3) {
    if ((n2 > n1) && (n3 > n2)) {
        return true;
    } else if ((n2 == n1) && (n3 == n1)) {
        return true;
    }
    return false;
  }

}