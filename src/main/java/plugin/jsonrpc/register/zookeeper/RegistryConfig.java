/*******************************************************************************
 *  Copyright (C) 2017 Holdings, Inc.
 *  All Rights Reserved.
 *
 *  This file is part of cloud service. Unauthorized copy of this file, via any
 *  medium is strictly prohibited. Proprietary and Confidential.
 *
 *  Author(s):
 *      Yunlong Liang <liangyunlong@qiyi.com>
*******************************************************************************/

package plugin.jsonrpc.register.zookeeper;

import java.util.Map;

public interface RegistryConfig {
    String getServiceName();

    void setServiceName(String serviceName);

    void setServiceAddress(String address);

    String getServiceAddress();

    void setServicePort(Integer port);

    Integer getServicePort();

    String getDiscoveryNamespace();

    void setDiscoveryNamespace(String namespace);

    String getRegistryNamespace();

    void setRegistryNamespace(String namespace);

    String getDiscoveryClassName();

    void setDiscoveryClassName(String discoveryClassName);

    String getRegistryClassName();

    void setRegistryClassName(String registryClassName);

    String getProfile();

    void setProfile(String profile);

    Map<String, String> getOptions();

    void setOptions(Map<String, String> options);

    void putOption(String key, String value);

    String getOption(String key);

    int getPriority();

    void setPriority(int priority);
}
