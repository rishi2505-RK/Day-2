class Solution {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length;
        int size = n / k;

        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
            if (freq.get(x) > k) return -1;
        }

        int totalMask = 1 << n;

        int[] cost = new int[totalMask];
        Arrays.fill(cost, -1);

        for (int mask = 0; mask < totalMask; mask++) {
            if (Integer.bitCount(mask) != size) continue;

            HashSet<Integer> set = new HashSet<>();
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    if (!set.add(nums[i])) {
                        valid = false;
                        break;
                    }
                    min = Math.min(min, nums[i]);
                    max = Math.max(max, nums[i]);
                }
            }

            if (valid) cost[mask] = max - min;
        }

        int INF = 1_000_000_000;
        int[] dp = new int[totalMask];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int mask = 0; mask < totalMask; mask++) {
            if (dp[mask] == INF) continue;

            int remain = (~mask) & (totalMask - 1);
            if (remain == 0) continue;

            int first = Integer.numberOfTrailingZeros(remain);

            for (int sub = remain; sub > 0; sub = (sub - 1) & remain) {
                if ((sub & (1 << first)) == 0) continue;
                if (cost[sub] == -1) continue;

                dp[mask | sub] = Math.min(dp[mask | sub], dp[mask] + cost[sub]);
            }
        }

        return dp[totalMask - 1] == INF ? -1 : dp[totalMask - 1];
    }
}