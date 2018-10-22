package appPackage;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class DumbPasswordEncoderTest {

    @Test
    public void encode() {
        DumbPasswordEncoder dumbPasswordEncoder = new DumbPasswordEncoder();

        Assert.assertEquals("secret: mypwd", dumbPasswordEncoder.encode("mypwd"));
        Assert.assertThat(dumbPasswordEncoder.encode("mypwd"), Matchers.containsString("mypwd"));
    }
}