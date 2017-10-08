
class Main {

	public static void main(String[] args) {

		Point p1 = new Point(3.0, 5.0);
		Point p2 = new Point(6.0, 8.0);
		Point p3 = new Point(9.0, 7.0);
		Point p4 = new Point(11.0, 9.0);
		Point p5 = new Point(13.0, 11.0);
		Point p6 = new Point(14.0, 10.0);
		Point p7 = new Point(16.0, 11.0);
		Point p8 = new Point(19.0, 11.0);
		Point p9 = new Point(21.0, 14.0);
		Point p10 = new Point(20.0, 13.0);
		Point p11 = new Point(22.0, 13.0);
		Point p12 = new Point(23.0, 16.0);
		Point p13 = new Point(24.0, 16.0);
		Point p14 = new Point(27.0, 16.0);

		Point[] points = {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14};
		DataSample ds = new DataSample(points);
		Regression m = new Regression(ds,3);
		m.set_use_coefficient_range(false);
		m.set_coefficient_steps(1000);
		m.set_error_threshold(0.01);
		m.start();

	}

}