package test;

import helpers.Command;
import org.junit.Assert;
import org.junit.Test;

public class TestCommand {
    @Test public void testGetCommandAndVal(){
        String s = Command.INFO +  Command.DIVIDER + "some info";
        Assert.assertArrayEquals(Command.getCommandAndVal(s), new String[]{Command.INFO, "some info"});
    }
}
