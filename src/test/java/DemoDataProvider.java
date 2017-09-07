/**
 * Created by liy58 on 9/6/17.
 */
import org.testng.annotations.DataProvider;

public class DemoDataProvider {
    @DataProvider(name = "DemoDataProvider")
    public static Object[][] DemoDataProvider(){
        return new Object[][] {{"MSFT","DD"}, {"NTAP","MSFT"}, {"CVT","CVT"},{"CVT","DVMT"},{"",""}};
    }
}
