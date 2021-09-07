package Base.View;

import Domain.Tweet;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class View implements PageApi {

    {
        printTitle();
    }

    public View() {
    }

    public SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    public String formatTweet(Tweet tweet) {
        StringBuilder formattedTweet = new StringBuilder();
        String[] twt = tweet.getTweetText().split(" ");
        for (int i = 0; i < twt.length; i++) {
            formattedTweet.append(twt[i]);
            if (i % 7 == 0 && i != 0)
                formattedTweet.append("\n");
            else
                formattedTweet.append(" ");
        }
        return formattedTweet.toString();
    }


    @Override
    public int printOptions(String... options) {
        int k = 0;
        for (Object opt : options) {
            System.out.println(++k + ". " + opt.toString());
        }
        return k;
    }


    @Override
    public <T> void print(T value) {
        System.out.println(value);
    }

    @Override
    public <T> void printTab(T value) {
        System.out.println("\t" + value);
    }

    @Override
    public void print() {
        System.out.println();
    }

    @Override
    public void line() {
        System.out.println("_".repeat(100));
    }

    @Override
    public void line75() {
        System.out.println("_".repeat(75));
    }

    @Override
    public void line(int i) {
        System.out.println("_".repeat(i));
    }

    @Override
    public void jump() {
        System.out.println("\n".repeat(4));

    }

    @Override
    public String enterValue(String msg, int minimumLength) {
        String val;
        System.out.print(msg + " > ");
        while (true) {
            val = new Scanner(System.in).next().strip();
            if (val.length() >= minimumLength)
                break;
            else
                warning("entered value must have at least " + minimumLength + " characters");
        }

        return val;
    }

    @Override
    public String enterLine(String msg) {
        String val;
        System.out.print(msg + " > ");
        val = new Scanner(System.in).nextLine();
        return val;
    }
    @Override
    public String enterLine280(String msg) {
        String val="";
        while (true) {
            System.out.print(msg + " : " + "\n >");
            val = new Scanner(System.in).nextLine();
            if (val.length() <= 280) break;
            print("you must enter 280 characters in comments");
        }
        return val;
    }

    @Override
    public String enterValue(String msg) {
        String val;
        System.out.print(msg + " > ");
        val = new Scanner(System.in).next();
        return val;
    }

    @Override
    public int selectOpt(int maxOpt) {
        print();
        int opt;
        print("enter an option between 1 to " + maxOpt);
        while (true) {
            try {
                opt = new Scanner(System.in).nextInt();
                if (opt > maxOpt)
                    warning("enter an option between 1 to " + maxOpt);
                else break;
            } catch (Exception e) {

                warning("insert correct value");
            }
        }
        success("selected " + opt);
        return opt;
    }

    @Override
    public <E> void printTitle(E value) {
        int l = value.toString().length();
        boolean even = l % 2 == 0;
        int dist;
        if (even) {
            int total = 96;
            dist = (total - l) / 2;
        } else {
            dist = (95 - l) / 2;

        }

        line();
        String k = " ";
        System.out.println("||" + " ".repeat(dist) + value + " ".repeat(dist - 1) + (even ? " " : "  ") + "||");
        print("||" + "_".repeat(96) + "||");
        print();
    }

    @Override
    public void printTitle() {
        String className = getClass().getName();
        String[] classNArr = className.split("\\.");
        className = classNArr[classNArr.length - 1];
        printTitle(className);

    }

    @Override
    public <K> void warning(K warningMessage) {
        System.out.println("[ Err ]: " + warningMessage);
    }

    @Override
    public void warning() {
        System.out.println(" [ Err ] ");

    }

    @Override
    public <Z> void success(Z warningMessage) {
        System.out.println("[ Ok ]: " + warningMessage);
    }

    @Override
    public void success() {
        System.out.println(" [ Ok ] ");
    }


}
