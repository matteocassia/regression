import java.lang.Math;
// ?

class LinearRegression extends Regression {

	public LinearRegression(DataSample data_sample) {
		super(data_sample, 2);
	}

}

class Regression {

	// Specifies whether coefficients are bounded or unbounded to a range; if unbounded, execution will only stop when the error threshold is reached
	private boolean use_coefficient_range = true;

	// The range that the values of the coefficients can have (e.g. if range equals 10, values will go from -5 to +5)
	private double coefficient_range = 10.0;

	// The steps that the values of the coefficients will take in the range (e.g. if the steps are 100 and the range is 10, one step will be taken every 0.1)
	private int coefficient_steps = 100;

	// The value of the error which is considered satisfying; once a set of coefficients is found bearing an error lower than the threshold, execution will stop
	private double error_threshold = 0.0;

	// The number of coefficients
	private int degree;

	// The sample data that the regression will apply to
	private DataSample data_sample;

	public Regression(DataSample data_sample, int degree) {
		this.data_sample = data_sample;
		this.degree = degree;
	}

	public void set_coefficient_range(double coefficient_range) {
		this.coefficient_range = coefficient_range;
	}

	public void set_coefficient_steps(int coefficient_steps) {
		this.coefficient_steps = coefficient_steps;
	}

	public void set_error_threshold(double error_threshold) {
		this.error_threshold = error_threshold;
	}

	public double get_coefficient_range() {
		return this.coefficient_range;
	}

	public int get_coefficient_steps() {
		return this.coefficient_steps;
	}

	public double get_error_threshold() {
		return this.error_threshold;
	}

	public void start() {
		double[] coefficients = new double[degree];
		set_initial_coefficients(coefficients);
		double lowest_error = -1;
		
		do {
			double error = calculate_error(coefficients);
			if(error <= lowest_error || lowest_error == -1) {
				lowest_error = error;
				print_report(coefficients, error);
				if(lowest_error <= error_threshold) {
					break;
				}
			}
			update_coefficients(coefficients);
		}
		while(can_continue(coefficients));
	}

	private boolean can_continue(double[] coefficients) {
		if(use_coefficient_range == false) {
			return true;
		}
		int count = 0;
		for(int i = 0; i < degree; i += 1) {
			if(coefficients[i] >= coefficient_range/2) {
				count += 1;
			}
		}
		return !(count == degree);
	}

	private void print_report(double[] coefficients, double error) {
		System.out.print("Coefficients:");
		for(int i = 0; i < degree; i++) {
			System.out.print(" " + coefficients[i]);
		}
		System.out.print("\n");
		System.out.println("Error: " + error);
		System.out.println("---------------");
		System.out.println(" ");

	}

	private double calculate_error(double[] coefficients) {
		double sum = 0;
		for(int i = 0; i < data_sample.get_number_of_points(); i += 1) {
			Point point = data_sample.get(i);
			double predicted_value = this.get_result(coefficients, point.get_x());
			double actual_value = point.get_y();
			double difference = predicted_value - actual_value;
			double x = difference * difference;
			sum += x;
		}
		double error = sum / (double)(data_sample.get_number_of_points());
		return error;
	}

	private double get_result(double[] coefficients, double value) {
		double result = 0;
		for(int i = 0; i < degree; i++) {
			double coefficient = coefficients[degree-1-i];
			double v = Math.pow(value, (double) i);
			result += coefficient * v;
		}
		return result;
	}

	private void set_initial_coefficients(double[] coefficients) {
		for(int i = 0; i < degree; i += 1) {
			coefficients[i] = -coefficient_range/2.0;
		}
	}

	private int get_number_of_combinations() {
		int total = (int) Math.pow((double) coefficient_steps, (double) degree);
		return total;
	}

	private void update_coefficients(double[] coefficients) {
		int i = 0;
		while(i < degree) {
			if(coefficients[i] < coefficient_range/2.0) {
				coefficients[i] += coefficient_range/(double)coefficient_steps;
				break;
			} else {
				coefficients[i] = -coefficient_range/2.0;
				i += 1;
			}
		}
	}



}

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

class Main {

	public static void main(String[] args) {
		/*
		Point p1 = new Point(1.0, 1.0);
		Point p2 = new Point(2.0, 2.0);
		Point[] points = {p1, p2};
		DataSample ds = new DataSample(points);
		Model m = new Model(2, ds);
		m.find();
		*/

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
		Regression m = new LinearRegression(ds);
		m.start();

	}

}






