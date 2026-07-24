class Solution {
    public int[] distributeCandies(int candies, int num_people) {
        int[] ans = new int[num_people];
        int give = 1;
        int index = 0;

        while (candies > 0) {
            ans[index] += Math.min(give, candies);
            candies -= give;
            give++;
            index = (index + 1) % num_people;
        }

        return ans;
    }
}