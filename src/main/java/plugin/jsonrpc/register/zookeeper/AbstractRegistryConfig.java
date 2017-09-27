package plugin.jsonrpc.register.zookeeper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistryConfig implements RegistryConfig {
    protected String serviceName;
    protected String serviceAddress;
    protected Integer servicePort;
    protected String discoveryNamespace;
    protected String registryNamespace;
    protected String discoveryClassName;
    protected String registryClassName;
    protected String profile;
    protected int priority = 0;
    protected Map<String,String> options = new ConcurrentHashMap<String, String>();

    @Override
    public String getServiceName() {
        return this.serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void setServiceAddress(String address) {
        this.serviceAddress = address;
    }

    @Override
    public String getServiceAddress() {
        return this.serviceAddress;
    }

    @Override
    public void setServicePort(Integer port) {
        this.servicePort = port;
    }

    @Override
    public Integer getServicePort() {
        return this.servicePort;
    }

    @Override
    public String getDiscoveryNamespace() {
        return this.discoveryNamespace;
    }

    @Override
    public void setDiscoveryNamespace(String namespace) {
        this.discoveryNamespace = namespace;
    }

    @Override
    public String getRegistryNamespace() {
        return this.registryNamespace;
    }

    @Override
    public void setRegistryNamespace(String namespace) {
        this.registryNamespace = namespace;
    }

    @Override
    public String getDiscoveryClassName() {
        return this.discoveryClassName;
    }

    @Override
    public void setDiscoveryClassName(String discoveryClassName) {
        this.discoveryClassName = discoveryClassName;
    }

    @Override
    public String getRegistryClassName() {
        return this.registryClassName;
    }

    @Override
    public void setRegistryClassName(String registryClassName) {
        this.registryClassName = registryClassName;
    }

    @Override
    public String getProfile() {
        return this.profile;
    }

    @Override
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public void putOption(String key, String value) {
        this.options.put(key, value);
    }

    public String getOption(String key) {
        return this.options.get(key);
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}