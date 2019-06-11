package com.github.saphyra.randwo.label.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.randwo.label.domain.Label;

@Component
public class LabelDao extends AbstractDao<LabelEntity, Label, UUID, LabelRepository> {
    public LabelDao(LabelConverter converter, LabelRepository repository) {
        super(converter, repository);
    }

    public Optional<Label> findByLabelValue(String labelValue) {
        return converter.convertEntity(repository.findByLabelValue(labelValue));
    }

    public List<Label> getAll() {
        return converter.convertEntity(repository.findAll());
    }
}
