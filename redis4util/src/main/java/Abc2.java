
public class Abc2 {

    public static void main(String[] args) {
        Abc2 abc2 = new Abc2();
        System.out.println(abc2 .abc("leetcode", "", new String[]{"leet", "code"}));;
        System.out.println(abc2.abc("applepenapple", "", new String[]{"apple", "pen"}));;
        System.out.println(abc2.abc("catsandog", "", new String[]{"cats", "dog", "sand", "and", "cat"}));
        System.out.println(abc2.abc("cars", "", new String[]{"car", "ca", "rs"}));
        System.out.println(abc2.abc("cbca", "", new String[]{"bc", "ca"}));
        System.out.println(abc2.abc("ccaccc", "", new String[]{"cc", "ac"}));
        System.out.println(abc2.abc("bccdbacdbdacddabbaaaadababadad", "", new String[]{"cbc","bcda","adb","ddca","bad","bbb","dad","dac","ba","aa","bd","abab","bb","dbda","cb","caccc","d","dd","aadb","cc","b","bcc","bcd","cd","cbca","bbd","ddd","dabb","ab","acd","a","bbcc","cdcbd","cada","dbca","ac","abacd","cba","cdb","dbac","aada","cdcda","cdc","dbc","dbcb","bdb","ddbdd","cadaa","ddbc","babb"}));
    }


    public boolean abc(String s, String test, String[] array) {
        for(String unit: array) {
            if((test + unit).equals(s)) {
                return true;
            }else if((test + unit).length() < s.length() && (test+unit).equals(s.substring(0, (test+unit).length()))){
                boolean flag = abc(s, test + unit, array);
                if(flag) {
                    return true;
                }
            }
        }
        if(test.equals("")) {
            return false;
        }
        return false;
    }
}
