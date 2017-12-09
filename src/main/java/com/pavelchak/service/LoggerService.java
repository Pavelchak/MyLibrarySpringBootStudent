package com.pavelchak.service;

import com.pavelchak.Repository.LoggerRepository;
import com.pavelchak.domain.Logger;
import com.pavelchak.exceptions.NoSuchLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoggerService {
    @Autowired
    LoggerRepository loggerRepository;

    public List<Logger> getAllLogger(){
        return loggerRepository.findAll();
    }

    public List<Logger> getLoggerFilterBySurname(String like){
        return loggerRepository.findByPersonLike(like+"%");
    }

    public Logger getLog(Long log_id) throws NoSuchLogException {
        Logger logger =loggerRepository.findOne(log_id);
        if(logger ==null) throw new NoSuchLogException();
        return logger;
    }

}
