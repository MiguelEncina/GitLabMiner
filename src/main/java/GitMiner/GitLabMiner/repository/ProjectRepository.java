package GitMiner.GitLabMiner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GitMiner.GitLabMiner.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    
}
