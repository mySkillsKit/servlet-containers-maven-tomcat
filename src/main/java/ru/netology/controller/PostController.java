package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {

    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }


    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }


    public void getById(long id, HttpServletResponse response) throws IOException {

        try {
            final var data = service.getById(id);
            response.setContentType(APPLICATION_JSON);
            final var gson = new Gson();
            response.getWriter().print(gson.toJson(data));

        } catch (NotFoundException e) {
            response.getWriter().print("Post not found {id:" + id + "}");
            e.printStackTrace();
        }

    }

    public void save(Reader body, HttpServletResponse response) throws IOException {

        try {
            response.setContentType(APPLICATION_JSON);
            final var gson = new Gson();
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));

        } catch (NotFoundException e) {
            response.getWriter().print("Post not saved");
            e.printStackTrace();
        }
    }


    public void removeById(long id, HttpServletResponse response) throws IOException {

        try {
            service.removeById(id);
            response.getWriter().print("Post with id#" + id + " deleted successful");

        } catch (NotFoundException e) {
            response.getWriter().print("Post not found {id:" + id + "}.Please check the id and try again.");
            e.printStackTrace();
        }


    }
}
