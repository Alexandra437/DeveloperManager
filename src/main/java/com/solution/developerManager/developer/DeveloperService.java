package com.solution.developerManager.developer;

import com.solution.developerManager.exception.DeveloperNotFoundException;
import lombok.RequiredArgsConstructor;
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
        return developerMapper.toDeveloperDto(developerRepository.save(developerMapper.toDeveloper(developerDto)));
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
