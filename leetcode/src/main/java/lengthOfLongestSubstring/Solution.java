package lengthOfLongestSubstring;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {

        System.out.println(new Solution().lengthOfLongestSubstring("aab"));;
        System.out.println(new Solution().lengthOfLongestSubstring2("aab"));;
    }

    public int lengthOfLongestSubstring(String s) {

        int maxLength = 0;
        int temp = 0;
        List list = new ArrayList<>();
        for(int i=0;i<s.length();i++) {

            if(list.contains(s.charAt(i))) {
                if(temp > maxLength) maxLength = temp;

                for(int j=0; j<list.size(); j++) {

                    if(list.get(j).equals(s.charAt(i))) {
                        list.remove(j);
                        break;
                    }

                    list.remove(j);
                    j--;
                }
                temp = list.size();
            }

            list.add(s.charAt(i));
            temp++;

        }

        if(temp > maxLength) maxLength = temp;

        return maxLength;
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
}
