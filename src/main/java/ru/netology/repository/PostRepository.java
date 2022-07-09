package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
public class PostRepository {

    private Map<Long, Post> postsListMap = new ConcurrentHashMap<>();
    private AtomicLong postCount = new AtomicLong(0);


    public List<Post> all() {
        // to convert map values to list
        return postsListMap.values().
                stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<Post> getById(long id) {

        for (Post post : postsListMap.values()) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();

    }

    public Post save(Post post) {

        if (post.getId() != 0) {
            if ((postsListMap.containsKey(post.getId()))) {
                postsListMap.put(post.getId(), post);
            } else {
                throw new NotFoundException("Post not saved {id:" + post.getId() + "}");
            }
        } else {
            // post.getId() == 0
            long id = postCount.incrementAndGet();
            post.setId(id);
            postsListMap.put(id, post);
        }
        return post;
    }

    public void removeById(long id) {

        if (postsListMap.containsKey(id)) {
            postsListMap.remove(id);
        } else {
            throw new NotFoundException("Post not found {id:" + id + "}.Please check the id and try again.");
        }
    }
}
