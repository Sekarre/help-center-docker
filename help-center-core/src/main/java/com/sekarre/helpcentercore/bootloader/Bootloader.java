package com.sekarre.helpcentercore.bootloader;

import com.sekarre.helpcentercore.domain.IssueType;
import com.sekarre.helpcentercore.domain.enums.IssueTypeName;
import com.sekarre.helpcentercore.repositories.IssueTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class Bootloader implements CommandLineRunner {

    private final IssueTypeRepository issueTypeRepository;

    @Override
    public void run(String... args) {
        loadIssueTypes();
    }

    private void loadIssueTypes() {
        if (issueTypeRepository.count() == 0) {
            for (IssueTypeName issueTypeName : IssueTypeName.values())
                issueTypeRepository.save(IssueType.builder()
                        .name(issueTypeName)
                        .isAvailable(true)
                        .build());
        }
    }
}
