import org.testng.Reporter;

import java.math.BigDecimal;

/**
 * Created by liy58 on 9/6/17.
 */
public class Helper {
    public void compare(String s, String s2, String c_name, String c2_name) throws Exception{
        BigDecimal eps_d = new BigDecimal(s);
        BigDecimal eps2_d = new BigDecimal(s2);
        if (eps_d.compareTo(eps2_d) == 1) {
            Reporter.log(c_name + " has a higher EPS " + eps_d);
        }else if (eps_d.compareTo(eps2_d) == -1){
            Reporter.log(c2_name + " has a higher EPS " + eps2_d);
        } else {
            Reporter.log("Both company have the same EPS");
        }
    }
    public void priceCompare(String l_h_v, String cur_price) throws Exception {
        String[] values = l_h_v.replace(" ", "").split("-");
        BigDecimal l_value = new BigDecimal(values[0]);
        BigDecimal h_value = new BigDecimal(values[1]);
        BigDecimal c_value = new BigDecimal(cur_price);
        BigDecimal l_percent, h_percent;
        if (h_value.subtract(c_value).compareTo(BigDecimal.ZERO) >= 0 &&
                (c_value.subtract(l_value).compareTo(BigDecimal.ZERO) >= 0)) {
            l_percent = h_value.subtract(c_value)
                    .multiply(new BigDecimal(100)).divide(h_value, 2, BigDecimal.ROUND_HALF_UP);
            h_percent = c_value.subtract(l_value)
                    .multiply(new BigDecimal(100)).divide(l_value, 2, BigDecimal.ROUND_HALF_UP);
            Reporter.log("Today's price of " + c_value + " is " + l_percent + "% lower than the 52 week high "
                    + h_value + " and " + h_percent + "% higher than the 52 week low " + l_value);
        } else if (h_value.subtract(c_value).compareTo(BigDecimal.ZERO) < 0) {
            h_percent = c_value.subtract(h_value).multiply(new BigDecimal(100)).divide(h_value, 2, BigDecimal.ROUND_HALF_UP);
            Reporter.log("Today's price of " + c_value + " is " + h_percent + "% higher than the 52 week high " + h_value);
        } else if (c_value.subtract(l_value).compareTo(BigDecimal.ZERO) < 0) {
            l_percent = l_value.subtract(c_value).multiply(new BigDecimal(100)).divide(l_value, 2, BigDecimal.ROUND_HALF_UP);
            Reporter.log("Today's price of " + c_value + " is " + l_percent + "% lower than the 52 week low" + l_value);
        }
    }
}
