public class Result {
    char op; 
    int r; // result of the operation

    Result(char op, int r) {
        this.op = op;
        this.r = r;
    }

    public char getOp() {
        return op;
    }

    public int getR() {
        return r;
    }
}
