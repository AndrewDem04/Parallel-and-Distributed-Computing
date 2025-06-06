public class Data {
    int a , b;
    char op; // +, -, *, /

    Data(int a, int b, char op) {
        this.a = a;
        this.b = b;
        this.op = op;
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    public char op() {
        return op;
    }
}
