package Base.View;

import Domain.Tweet;

public interface PageApi {

    <T> void print(T value);
    <T> void printTab(T value);

    void print();

    void line();

    void line75();

    void line(int i);

    void jump();

    String enterValue(String msg, int minimumLength);

    String enterLine(String msg );

    String enterLine280(String msg);

    String enterValue(String msg);

    int selectOpt(int maxOpt);

    <E> void printTitle(E value);

    void printTitle();


    <K> void warning(K warningMessage);

    void warning();

    <Z> void success(Z warningMessage);

    void success();

    String formatTweet(Tweet tweet);

    int printOptions(String ... options);

}
