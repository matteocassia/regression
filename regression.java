import java.lang.Math;
// ?

class LinearRegression extends Regression {

	public LinearRegression(DataSample data_sample) {
		super(data_sample, 2);
	}

}

class Regression {

	private final double[] DEFAULT_COEFFICIENT_RANGE = {-5.0, +5.0};
	private final int DEFAULT_COEFFICIENT_STEPS = 10;
	private final double DEFAULT_ERROR_TRESHOLD = 1.0;

	private int rounds = 0;

	// Specifies whether coefficients are bounded or unbounded to a range; if unbounded, execution will only stop when the error threshold is reached
	private boolean use_coefficient_range = true;

	// The range that the values of the coefficients can have (e.g. if range equals 10, values will range from -5 to +5)
	private double[] coefficient_range = DEFAULT_COEFFICIENT_RANGE;

	// The steps that the values of the coefficients will take in the range (e.g. if the steps are 100 and the range is 10, one step will be taken every 0.1)
	private int coefficient_steps = DEFAULT_COEFFICIENT_STEPS;

	// The value of the error which is considered satisfying; once a set of coefficients is found bearing an error lower than the threshold, execution will stop
	private double error_threshold = DEFAULT_ERROR_TRESHOLD;

	// The number of coefficients
	private int degree;

	// The sample data that the regression will apply to
	private DataSample data_sample;

	public Regression(DataSample data_sample, int degree) {
		this.data_sample = data_sample;
		this.degree = degree;
	}

	public void set_coefficient_range(double[] coefficient_range) {
		this.coefficient_range = coefficient_range;
	}

	public void set_coefficient_steps(int coefficient_steps) {
		this.coefficient_steps = coefficient_steps;
	}

	public void set_error_threshold(double error_threshold) {
		this.error_threshold = error_threshold;
	}

	public void set_use_coefficient_range(boolean use_coefficient_range) {
		this.use_coefficient_range = use_coefficient_range;
	}

	public double[] get_coefficient_range() {
		return this.coefficient_range;
	}

	public int get_coefficient_steps() {
		return this.coefficient_steps;
	}

	public double get_error_threshold() {
		return this.error_threshold;
	}

	public boolean get_use_coefficient_range() {
		return this.use_coefficient_range;
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

				if(!use_coefficient_range) {
					if(error <= error_threshold) {
						break;
					}
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
			if(coefficients[i] >= coefficient_range[1]) {
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
			coefficients[i] = coefficient_range[0];
		}
	}

	private int get_number_of_combinations() {
		int total = (int) Math.pow((double) coefficient_steps, (double) degree);
		return total;
	}

	private void update_coefficients(double[] coefficients) {
		int i = 0;
		while(true) {
			if(coefficients[i] < coefficient_range[1]) {
				coefficients[i] += (coefficient_range[1]-coefficient_range[0])/(double)coefficient_steps;
				break;
			} else {
				if(i == degree - 1) {
					if(use_coefficient_range) {
						break;
					} else {
						rounds += 1;
						double a = coefficient_range[0];
						double b = coefficient_range[1];
						double d = (b-a);
						if(rounds % 2 == 0) {
							coefficient_range[0] += d*rounds;
							coefficient_range[1] += d*rounds;
						} else {
							coefficient_range[0] -= d*rounds;
							coefficient_range[1] -= d*rounds;
						}
						set_initial_coefficients(coefficients);
						i = 0;
						break;
					}
				} else {
					coefficients[i] = coefficient_range[0];
					i += 1;
				}
			}
		}
	}

}