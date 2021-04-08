import java.util.*;

public class Example2 {

    public static void main(String[] args) {
        arithmeticDemo();
    }

    // demo if/else
    public static printElseThree(int numTimes) {
        if (numTimes < 3 && numTimes > -100) {
            System.out.println("it's less");
        } else if (numTimes == 3) {
            System.out.println("it's three");
        } else {
            System.out.println("it's more, probably");
        }
    }

    // demo while/switch
    public String stringWhile() {
        String var = "";
        while (var.length < 100) {
            switch (var.length) {
                case 10:
                    var += "ten";
                    break;
                default:
                    var += "asdfj";
            }
        }
    }

    // for, while, if
    public void arithmeticDemo() {
        int j = 0;
        while (j == 0) {
            j += 4;
            j -= 5;
            j *= 20;
            for (int i = 0; i <= 5; i++) {
                j /= 2;
            }
            if (j <= j % 1 || j < j % 2) {
                j = j - 1;
                j = j + 1;
                j = j * 4;
                j = j / 2;
            } else if (j != 0 && j >= j * 1) {
                j = j--;
            } else {
                System.out.println(j);
            }
        }
    }
}


