class DataSample {

	private Point[] points;

	public DataSample(Point[] points) {
		this.points = points;
	}

	public Point[] get_points() {
		return points;
	}

	public Point get(int n) {
		return points[n];
	}

	public int get_number_of_points() {
		return points.length;
	}

}

class Point {

	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double get_x() {
		return x;
	}

	public double get_y() {
		return y;
	}

}