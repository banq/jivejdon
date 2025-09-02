import com.jdon.jivejdon.util.PinyinUtils;

public class TestPinyin {
    public static void main(String[] args) {
        // 测试包含特殊字符的情况
        String test1 = "战略性";
        String result1 = PinyinUtils.toPinyin(test1);
        System.out.println("输入: " + test1);
        System.out.println("输出: " + result1);
        System.out.println();
        
        // 测试包含空格的情况
        String test2 = "战略性 规划";
        String result2 = PinyinUtils.toPinyin(test2);
        System.out.println("输入: " + test2);
        System.out.println("输出: " + result2);
        System.out.println();
        
        // 测试混合内容
        String test3 = "Hello世界123";
        String result3 = PinyinUtils.toPinyin(test3);
        System.out.println("输入: " + test3);
        System.out.println("输出: " + result3);
    }
}