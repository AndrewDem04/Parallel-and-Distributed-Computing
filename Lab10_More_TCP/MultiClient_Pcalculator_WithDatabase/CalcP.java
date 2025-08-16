public class CalcP {
    
    public double CalcP(int n){
        double step = 1.0 / n;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double x = (i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }
        return step * sum;
    }
}

