import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.File;
import java.util.ArrayList;

public class Stage1Test extends StageTest {
    @DynamicTest
    CheckResult test1() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start("./src/scrabbleTilesDistribution.txt");
        String[] p1 = output.trim().replace(System.getProperty("line.separator")+System.getProperty("line.separator"),"\n").split("\n");
        ArrayList<String> p = new ArrayList<>();
        for (String s : p1) {
            if (s.trim().length() != 0) {
                p.add(s);
            }
        }
        if(p.size()<2){
            throw new WrongAnswer("""
                    Your output is not formatted correctly. It should be as follows:
                    "Welcome to Crossword Scrabble
                    Bag: 'tiles distribution'\"""");
        }
        if(!p.get(0).toLowerCase().trim().contains("Welcome to Crossword Scrabble".toLowerCase())){
            throw new WrongAnswer("Your output should contain the game tittle " +
                    "\"Welcome to Crossword Scrabble\"");
        }
        if (!p.get(1).startsWith("Bag: ") ){
            throw new WrongAnswer("Your output for bag should start with " +
                    "\"Bag: \",");
        }
        if (p.get(1).length()< 105){
            throw new WrongAnswer("Your have less tiles than expected ");
        }
        if (p.get(1).length() > 105){
            throw new WrongAnswer("Your have more tiles than expected.");
        }
        if (!p.get(1).contains("AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ**") ){
            throw new WrongAnswer("Your bag contains wrong tiles distribution ");
        }

        //output = pr.execute("Line1\nLine2\nline3\n");
        return CheckResult.correct();
    }
    Object[][] wrongFiles =  {
            {"./test/scrabbleTilesDistribution_1.txt"},
            {"./test/scrabbleTilesDistribution_2.txt"},
            {"./test/scrabbleTilesDistribution_3.txt"},
            {"./test/scrabbleTilesDistribution_4.txt"}
    };
    @DynamicTest(data = "wrongFiles")
    CheckResult test6( String message) {
        String bag ="AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ**";
        TestedProgram pr2 = new TestedProgram();
        String output = pr2.start(message);
        if(output.contains(bag)){
            throw new WrongAnswer("You program is not reading the correct file.");
        }
        return CheckResult.correct();
    }
}