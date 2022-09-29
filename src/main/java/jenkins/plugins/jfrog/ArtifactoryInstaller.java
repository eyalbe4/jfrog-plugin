package jenkins.plugins.jfrog;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.tools.ToolInstallation;
import jenkins.plugins.jfrog.configuration.JFrogPlatformInstance;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.List;

import static jenkins.plugins.jfrog.configuration.JfrogPlatformBuilder.getJFrogPlatformInstances;

public class ArtifactoryInstaller extends BinaryInstaller {

    public final String id;

    public final String repository;

    @DataBoundConstructor
    public ArtifactoryInstaller(String id, String repository) {
        super(null);
        this.id = id;
        this.repository = repository;
    }

    @Override
    public FilePath performInstallation(ToolInstallation tool, Node node, TaskListener log) throws IOException, InterruptedException {
        JFrogPlatformInstance server = getSpecificServer(id);
        if (server == null){
            throw new IOException("Server id \'"+id+"\' doesn't exists.");
        }
        return Utils.performJfrogCliInstallation(getToolLocation(tool, node), log, "", server, repository);
    }

    /**
     * Provide all configured server ids and return the specific one with the given id.
     */
    private JFrogPlatformInstance getSpecificServer(String id) {
        List<JFrogPlatformInstance> jfrogInstances = getJFrogPlatformInstances();
        if (jfrogInstances != null && jfrogInstances.size() > 0) {
            for (JFrogPlatformInstance jfrogPlatformInstance : jfrogInstances) {
                if (jfrogPlatformInstance.getId().equals(id)) {
                    return jfrogPlatformInstance;
                }
            }
        }
        return null;
    }

    @Extension
    public static final class DescriptorImpl extends BinaryInstaller.DescriptorImpl<ArtifactoryInstaller> {
        public String getDisplayName() {
            return "Install from Artifactory";
        }

        @Override
        public boolean isApplicable(Class<? extends ToolInstallation> toolType) {
            return toolType== JfrogInstallation.class;
        }

        /**
         * Necessary for displaying all configured server Ids.
         * @return All pre configured servers Ids
         */
        public List<JFrogPlatformInstance> getServerIds() {
            return getJFrogPlatformInstances();
        }
    }
}