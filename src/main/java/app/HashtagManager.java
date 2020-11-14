package app;

import server.database.dao.HashtagDAO;
import server.database.model.Hashtag;

import java.util.*;

public class HashtagManager {
    public static boolean createHashTag(int postID, String message){
        Set<String> hashtags = getHashtags(message);
        Iterator<String> it = hashtags.iterator();
        boolean success = true;

        if(!hashtags.isEmpty()) {
            while(it.hasNext() && success) {
                String hashtagWord = it.next();

                Hashtag hashtag = HashtagDAO.selectByHashtag(hashtagWord);

                Integer hashtagId;
                if (hashtag == null)
                    hashtagId = HashtagDAO.insert(postID, hashtagWord);
                else
                    hashtagId = hashtag.getId();

                if (hashtagId != null){
                    success = HashtagDAO.insertPostHashTag(postID, hashtagId);
                }
            }
        }

        return success;
    }

    public static Set<String> getHashtags(String message) {
        String[] words = message.split("\\s+");
        Set<String> hashtags = new HashSet<String>();
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags.add(word.substring(1));
            }
        }
        return hashtags;
    }

    public static ArrayList<Integer> getPostIDsByHashtags(List<String> hashtags){
        return HashtagDAO.getPostIDsByHashtags(hashtags);
    }

    public static boolean existsInPostHashtag(int postId){
        return HashtagDAO.existsInPostHashtag(postId);
    }

    public static boolean deletePostHashTag(int postId){
        return HashtagDAO.deletePostHashTag(postId);
    }
}
