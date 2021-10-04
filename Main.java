package src.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Main {
    static class Bot {
        Robot robot;

        public Bot() throws AWTException {
            this.robot = new Robot();
        }

        public Bot(Robot robot) {
            this.robot = robot;
        }

        public void leftClick() {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

        public void rightClick() {
            robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        }

        public void leftHold() {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        }

        public void leftUnhold() {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

        public void rightHold() {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        }

        public void rightUnhold() {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

        // moves mouse to coords (pixels counted from top left)
        public void moveMouse(double xCord, double yCord) {
            // fixing coordinates according to 1920x1080 screen pixels
            xCord /= 1.25;
            yCord /= 1.25;

            int x = (int) Math.round(xCord);
            int y = (int) Math.round(yCord);
            robot.mouseMove(x, y);
        }

        // paste a string
        public void paste(String text) {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }

        // type a string character by character
        public void type(String keys, int delay) {
            for (char c : keys.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                    throw new RuntimeException("Key code not found for character '" + c + "'");
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.delay(delay);
            }
        }

        public void keyClick(int key) {
            robot.keyPress(key);
            robot.keyRelease(key);
        }

        // hax
        public void autoClicker(int cps, int clicks) {
            while (clicks-- > 0) {
                leftClick();
                robot.delay(Math.round(1000 / cps));
            }
        }
    }

    public static void main(String[] args) {
        Bot bot;
        try {

            bot = new Bot();
            bot.leftClick();
            bot.paste("Hello World!");

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
