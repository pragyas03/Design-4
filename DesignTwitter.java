/* 

Time Complexity : 
getNewsFeed(): O(Nlog k),N is total number of tweets, k is number of followees
postTweet(): O(1)
follow(): O(1)
unfollow(): O(1)
Space Complexity : O(1)
Did this code successfully run on Leetcode : Yes

*/

class Twitter {

    Map<Integer,Set<Integer>> followers;
    Map<Integer,List<Tweet>> tweets;
    int x;

    /** Initialize your data structure here. */
    public Twitter() {

        followers = new HashMap<>();
        tweets = new HashMap<>();

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {

        follow(userId,userId);

        if(!tweets.containsKey(userId)){
            tweets.put(userId,new LinkedList<>());
        }

        tweets.get(userId).add(new Tweet(tweetId,x++));

    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.time-b.time);

        Set<Integer> set = followers.get(userId);

        // Iterate through all the tweets and get the recent posts
        if(set != null){
            for(int each:set){

                List<Tweet> list = tweets.get(each);

                if(list != null){
                    for(Tweet t:list){
                        pq.add(t);

                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }


            }
        }

        List<Integer> output = new ArrayList<>();

        while(!pq.isEmpty()){
            output.add(0,pq.poll().tweetId);
        }

        return output;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {

        if(!followers.containsKey(followerId)){
            followers.put(followerId,new HashSet<>());
        }

        followers.get(followerId).add(followeeId);

    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {


        if(followers.containsKey(followerId) && followerId!=followeeId){
            followers.get(followerId).remove(followeeId);
        }

    }
}

class Tweet{

    int tweetId;
    int time;

    public Tweet(int id,int t){
        this.tweetId=id;
        this.time=t;
    }
}