package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;

import java.util.List;

public interface BlogService extends Service<Blog> {

    List<Blog> findAllByCategory(Category category);

}
