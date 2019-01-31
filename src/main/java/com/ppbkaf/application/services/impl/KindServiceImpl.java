package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.KindRepository;
import com.ppbkaf.application.services.KindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KindServiceImpl implements KindService {

    private KindRepository kindRepository;

    @Autowired
    public KindServiceImpl(KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }

    @Override
    public List<Kind> getAll() {
        try {
            return kindRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all kinds", ex);
        }
    }

    @Override
    public Kind get(int id) {
        try {
            return kindRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get kind", ex);
        }
    }

    @Override
    public int add(Kind obj) {
        try {
            return kindRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);
            throw new AddException("Cannot add kind", ex);
        }
    }

    @Override
    public void update(int id, Kind obj) {
        try {
            kindRepository.update(id, obj);
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update kind", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            kindRepository.delete(id);
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete kind", ex);
        }
    }
}
