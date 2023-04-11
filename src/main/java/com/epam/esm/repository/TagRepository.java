package com.epam.esm.repository;

import com.epam.esm.model.impl.Tag;

import java.util.List;

public interface TagRepository<T extends Tag> extends CRDRepository<T> {

    List<T> getByIdList(List<Integer> idList);

    T getByName(String name);
}
