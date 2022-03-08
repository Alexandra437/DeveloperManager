package com.solution.developerManager.developer;

import com.solution.developerManager.exception.DeveloperNotFoundException;
import com.solution.developerManager.exception.NotUniqueDeveloperEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    final DeveloperRepository developerRepository;
    final DeveloperMapper developerMapper;

    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    public DeveloperDto findDeveloperByEmail(String email) {
        if (developerRepository.existsDeveloperByEmail(email)) {
            return developerMapper.toDeveloperDto(developerRepository.findByEmail(email));
        } else {
            throw new DeveloperNotFoundException("Developer not found");
        }
    }

    @Transactional
    public void deleteDeveloperByEmail(String email) {
        if (developerRepository.existsDeveloperByEmail(email)) {
            developerRepository.deleteDeveloperByEmail(email);
        } else {
            throw new DeveloperNotFoundException("Developer not found");
        }
    }

    public DeveloperDto saveDeveloper(DeveloperDto developerDto) {
        try {
            return developerMapper.toDeveloperDto(developerRepository.save(developerMapper.toDeveloper(developerDto)));
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueDeveloperEmailException("Developer with this email is already exist!!!");
        }
    }

    @Transactional
    public DeveloperDto updateDeveloper(DeveloperDto developerDto, String developerEmail) {
        if (developerRepository.existsDeveloperByEmail(developerEmail)) {
            Developer developer = developerRepository.findByEmail(developerEmail);
            developer.setName(developerDto.getName());
            developer.setEmail(developerDto.getEmail());
            return developerMapper.toDeveloperDto(developerRepository.save(developer));
        } else {
            throw new DeveloperNotFoundException("Developer for update not found");
        }
    }
}
