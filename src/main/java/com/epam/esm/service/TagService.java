package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        return tagRepository.getAll();
    }

    public Tag getById(int id) {
        return tagRepository.getById(id);
    }

    public int create(Tag tag) {
        return tagRepository.create(tag);
    }

    public int delete(int id) {
        return tagRepository.delete(id);
    }
}
