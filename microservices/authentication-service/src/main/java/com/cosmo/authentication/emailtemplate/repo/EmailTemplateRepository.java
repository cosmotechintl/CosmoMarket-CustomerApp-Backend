package com.cosmo.authentication.emailtemplate.repo;

import com.cosmo.authentication.emailtemplate.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {
    EmailTemplate findEmailTemplateByName(String name);
}
