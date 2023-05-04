package GitMiner.GitLabMiner.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import GitMiner.GitLabMiner.model.Namespace;
import GitMiner.GitLabMiner.model.Project;
import GitMiner.GitLabMiner.service.ProjectService;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService service;

    @Test
    @DisplayName("Get Namespace")
    void testFindNamespace() {
        Project project = service.findProject("4207231", "", "","");
        System.out.println(project);
    }
}
