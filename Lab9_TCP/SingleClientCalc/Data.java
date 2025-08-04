public class Data {
    int a , b;
    char op; // +, -, *, /

    Data(int a, int b, int op2) {
        this.a = a;
        this.b = b;
        this.op = (char) op2;
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
