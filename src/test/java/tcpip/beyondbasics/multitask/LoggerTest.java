package tcpip.beyondbasics.multitask;

import java.util.logging.Logger;

/**
 * Created by Chen Li on 2017/2/5.
 */
public class LoggerTest {
  public static void main(String[] args) {
    Logger logger = Logger.getLogger("LoggerTest");
    logger.info("hello");

  }
}
