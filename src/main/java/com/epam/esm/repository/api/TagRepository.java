package com.epam.esm.repository.api;

import com.epam.esm.model.impl.Tag;

import java.util.List;

public interface TagRepository<T extends Tag> extends CRDRepository<T> {

    List<T> getByIdList(List<Integer> idList);

    T getByName(String name);
}
