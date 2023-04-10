package com.epam.esm.repository;

import com.epam.esm.model.Entity;
import com.epam.esm.model.impl.Tag;

import java.util.List;

public interface TagRepository<T extends Entity> extends CRDRepository<T> {

    List<Tag> getByIdList(List<Integer> idList);

    Tag getByName(String name);
}
