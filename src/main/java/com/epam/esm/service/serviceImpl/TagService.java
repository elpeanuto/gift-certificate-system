package com.epam.esm.service.serviceImpl;

import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRDRepository;
import com.epam.esm.repository.repositoryImpl.TagRepository;
import com.epam.esm.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements CRDService<Tag> {

    private final CRDRepository<Tag> tagRepository;

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
