/*
 * Copyright 2015-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.deployer.spi.kubernetes;

import java.util.ArrayList;
import java.util.List;

import io.fabric8.kubernetes.api.model.NodeAffinity;
import io.fabric8.kubernetes.api.model.PodAffinity;
import io.fabric8.kubernetes.api.model.PodAntiAffinity;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.client.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static org.springframework.cloud.deployer.spi.kubernetes.KubernetesDeployerProperties.KUBERNETES_DEPLOYER_PROPERTIES_PREFIX;

/**
 * @author Florian Rosenberg
 * @author Thomas Risberg
 * @author Donovan Muller
 * @author Ilayaperumal Gopinathan
 * @author Leonardo Diniz
 * @author Chris Schaefer
 * @author David Turanski
 * @author Enrique Medina Montenegro
 */
@ConfigurationProperties(prefix = KUBERNETES_DEPLOYER_PROPERTIES_PREFIX)
public class KubernetesDeployerProperties {
	static final String KUBERNETES_DEPLOYER_PROPERTIES_PREFIX = "spring.cloud.deployer.kubernetes";

	/**
	 * Constants for app deployment properties that don't have a deployer level default property.
	 */
	static final String KUBERNETES_DEPLOYMENT_NODE_SELECTOR = "spring.cloud.deployer.kubernetes.deployment.nodeSelector";

	/**
	 * The maximum concurrent tasks allowed for this platform instance.
	 */
	private int maximumConcurrentTasks = 20;

	@NestedConfigurationProperty
	private Config fabric8 = Config.autoConfigure(null);

	public Config getFabric8() {
		return this.fabric8;
	}

	public void setFabric8(Config fabric8) {
		this.fabric8 = fabric8;
	}

	/**
	 * Encapsulates resources for Kubernetes Container resource limits
	 */
	public static class LimitsResources {

		/**
		 * Container resource cpu limit.
		 */
		private String cpu;

		/**
		 * Container resource memory limit.
		 */
		private String memory;

		/**
		 * Container GPU vendor name for limit
		 */
		private String gpuVendor;

		/**
		 * Container GPU count for limit.
		 */
		private String gpuCount;

		public LimitsResources() {
		}

		/**
		 * 'All' args constructor
		 * @deprecated
		 * This method should no longer be used to set all fields at construct time.
		 * <p> Use the default constructor and set() methods instead.
		 * @param cpu Container resource cpu limit
		 * @param memory Container resource memory limit
		 */
		@Deprecated
		public LimitsResources(String cpu, String memory) {
			this.cpu = cpu;
			this.memory = memory;
		}

		public String getCpu() {
			return cpu;
		}

		public void setCpu(String cpu) {
			this.cpu = cpu;
		}

		public String getMemory() {
			return memory;
		}

		public void setMemory(String memory) {
			this.memory = memory;
		}

		public String getGpuVendor() {
			return gpuVendor;
		}

		public void setGpuVendor(String gpuVendor) {
			this.gpuVendor = gpuVendor;
		}

		public String getGpuCount() {
			return gpuCount;
		}

		public void setGpuCount(String gpuCount) {
			this.gpuCount = gpuCount;
		}
	}

	/**
	 * Encapsulates resources for Kubernetes Container resource requests
	 */
	public static class RequestsResources {

		/**
		 * Container request limit.
		 */
		private String cpu;

		/**
		 * Container memory limit.
		 */
		private String memory;

		public RequestsResources() {
		}

		public RequestsResources(String cpu, String memory) {
			this.cpu = cpu;
			this.memory = memory;
		}

		public String getCpu() {
			return cpu;
		}

		public void setCpu(String cpu) {
			this.cpu = cpu;
		}

		public String getMemory() {
			return memory;
		}

		public void setMemory(String memory) {
			this.memory = memory;
		}
	}

	public static class StatefulSet {

		private VolumeClaimTemplate volumeClaimTemplate = new VolumeClaimTemplate();

		public VolumeClaimTemplate getVolumeClaimTemplate() {
			return volumeClaimTemplate;
		}

		public void setVolumeClaimTemplate(VolumeClaimTemplate volumeClaimTemplate) {
			this.volumeClaimTemplate = volumeClaimTemplate;
		}

		public static class VolumeClaimTemplate {

			/**
			 * VolumeClaimTemplate storage.
			 */
			private String storage = "10m";

			/**
			 * VolumeClaimTemplate storage class name.
			 */
			private String storageClassName;

			public String getStorage() {
				return storage;
			}

			public void setStorage(String storage) {
				this.storage = storage;
			}

			public String getStorageClassName() {
				return storageClassName;
			}

			public void setStorageClassName(String storageClassName) {
				this.storageClassName = storageClassName;
			}
		}
	}

	public static class Toleration {

		private String effect;

		private String key;

		private String operator;

		private Long tolerationSeconds;

		private String value;

		public String getEffect() {
			return effect;
		}

		public void setEffect(String effect) {
			this.effect = effect;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public Long getTolerationSeconds() {
			return tolerationSeconds;
		}

		public void setTolerationSeconds(Long tolerationSeconds) {
			this.tolerationSeconds = tolerationSeconds;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	static class KeyRef {
		private String envVarName;
		private String dataKey;

		public void setEnvVarName(String envVarName) {
			this.envVarName = envVarName;
		}

		public String getEnvVarName() {
			return envVarName;
		}

		public void setDataKey(String dataKey) {
			this.dataKey = dataKey;
		}

		public String getDataKey() {
			return dataKey;
		}
	}

	public static class SecretKeyRef extends KeyRef {
		private String secretName;

		public void setSecretName(String secretName) {
			this.secretName = secretName;
		}

		public String getSecretName() {
			return secretName;
		}
	}

	public static class ConfigMapKeyRef extends KeyRef {
		private String configMapName;

		public void setConfigMapName(String configMapName) {
			this.configMapName = configMapName;
		}

		public String getConfigMapName() {
			return configMapName;
		}
	}

	public static class PodSecurityContext {
		private Long runAsUser;
		private Long fsGroup;

		public void setRunAsUser(Long runAsUser) {
			this.runAsUser = runAsUser;
		}

		public Long getRunAsUser() {
			return this.runAsUser;
		}

		public void setFsGroup(Long fsGroup) {
			this.fsGroup = fsGroup;
		}

		public Long getFsGroup() {
			return fsGroup;
		}
	}

	public static class InitContainer {
		private String imageName;
		private String containerName;
		private List<String> commands;

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public String getContainerName() {
			return containerName;
		}

		public void setContainerName(String containerName) {
			this.containerName = containerName;
		}

		public List<String> getCommands() {
			return commands;
		}

		public void setCommands(List<String> commands) {
			this.commands = commands;
		}
	}


	/**
	 * Name of the environment variable that can define the Kubernetes namespace to use.
	 */
	public static final String ENV_KEY_KUBERNETES_NAMESPACE = "KUBERNETES_NAMESPACE";

	private static String KUBERNETES_NAMESPACE = System.getenv("KUBERNETES_NAMESPACE");

	/**
	 * Namespace to use.
	 */
	private String namespace = KUBERNETES_NAMESPACE;

	/**
	 * Secrets for a access a private registry to pull images.
	 */
	private String imagePullSecret;

	/**
	 * Delay in seconds when the Kubernetes liveness check of the app container
	 * should start checking its health status.
	 */
	// See https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int livenessProbeDelay = 10;

	/**
	 * Period in seconds for performing the Kubernetes liveness check of the app container.
	 */
	// See https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int livenessProbePeriod = 60;

	/**
	 * Timeout in seconds for the Kubernetes liveness check of the app container.
	 * If the health check takes longer than this value to return it is assumed as 'unavailable'.
	 */
	// see https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int livenessProbeTimeout = 2;

	/**
	 * Path that app container has to respond to for liveness check.
	 */
	// See https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private String livenessProbePath;

	/**
	 * Port that app container has to respond on for liveness check.
	 */
	private Integer livenessProbePort = null;

	/**
	 * Delay in seconds when the readiness check of the app container
	 * should start checking if the module is fully up and running.
	 */
	// see https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int readinessProbeDelay = 10;

	/**
	 * Period in seconds to perform the readiness check of the app container.
	 */
	// see https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int readinessProbePeriod = 10;

	/**
	 * Timeout in seconds that the app container has to respond to its
	 * health status during the readiness check.
	 */
	// see https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private int readinessProbeTimeout = 2;

	/**
	 * Path that app container has to respond to for readiness check.
	 */
	// See https://kubernetes.io/v1.0/docs/user-guide/production-pods.html#liveness-and-readiness-probes-aka-health-checks}
	private String readinessProbePath;

	/**
	 * Port that app container has to respond on for readiness check.
	 */
	private Integer readinessProbePort = null;

	/**
	 * The secret name containing the credentials to use when accessing secured probe endpoints.
	 */
	private String probeCredentialsSecret;

	/**
	 * Memory and CPU limits (i.e. maximum needed values) to allocate for a Pod.
	 */
	private LimitsResources limits = new LimitsResources();

	/**
	 * Memory and CPU requests (i.e. guaranteed needed values) to allocate for a Pod.
	 */
	private RequestsResources requests = new RequestsResources();

	/**
	 * Tolerations to allocate for a Pod.
	 */
	private List<Toleration> tolerations = new ArrayList<>();

	/**
	 * Secret key references to be added to the Pod environment.
	 */
	private List<SecretKeyRef> secretKeyRefs = new ArrayList<>();

	/**
	 * ConfigMap key references to be added to the Pod environment.
	 */
	private List<ConfigMapKeyRef> configMapKeyRefs = new ArrayList<>();

	/**
	 * ConfigMap references to be added to the Pod environment.
	 */
	private List<String> configMapRefs = new ArrayList<>();

	/**
	 * Secret references to be added to the Pod environment.
	 */
	private List<String> secretRefs = new ArrayList<>();

	/**
	 * Resources to assign for VolumeClaimTemplates (identified by metadata name) inside StatefulSet.
	 */
	private StatefulSet statefulSet = new StatefulSet();

	/**
	 * Environment variables to set for any deployed app container. To be used for service binding.
	 */
	private String[] environmentVariables = new String[]{};

	/**
	 * Entry point style used for the Docker image. To be used to determine how to pass in properties.
	 */
	private EntryPointStyle entryPointStyle = EntryPointStyle.exec;

	/**
	 * Create a "LoadBalancer" for the service created for each app. This facilitates assignment of external IP to app.
	 */
	private boolean createLoadBalancer = false;

	/**
	 * Service annotations to set for the service created for each app.
	 */
	private String serviceAnnotations = null;

	/**
	 * Pod annotations to set for the pod created for each deployment.
	 */
	private String podAnnotations;

	/**
	 * Job annotations to set for the pod or job created for a job.
	 */
	private String jobAnnotations;

	/**
	 * Time to wait for load balancer to be available before attempting delete of service (in minutes).
	 */
	private int minutesToWaitForLoadBalancer = 5;

	/**
	 * Maximum allowed restarts for app that fails due to an error or excessive resource use.
	 */
	private int maxTerminatedErrorRestarts = 2;

	/**
	 * Maximum allowed restarts for app that is in a CrashLoopBackOff.
	 */
	private int maxCrashLoopBackOffRestarts = 4;

	/**
	 * The image pull policy to use for Pod deployments in Kubernetes.
	 */
	private ImagePullPolicy imagePullPolicy = ImagePullPolicy.IfNotPresent;

	/**
	 * Volume mounts that a container is requesting.
	 * This can be specified as a deployer property or as an app deployment property.
	 * Deployment properties will override deployer properties.
	 */
	private List<VolumeMount> volumeMounts = new ArrayList<>();

	/**
	 * The volumes that a Kubernetes instance supports.
	 * See https://kubernetes.io/docs/user-guide/volumes/#types-of-volumes
	 * This can be specified as a deployer property or as an app deployment property.
	 * Deployment properties will override deployer properties.
	 */
	private List<Volume> volumes = new ArrayList<>();

	/**
	 * The hostNetwork setting for the deployments.
	 * See https://kubernetes.io/docs/api-reference/v1/definitions/#_v1_podspec
	 * This can be specified as a deployer property or as an app deployment property.
	 * Deployment properties will override deployer properties.
	 */
	private boolean hostNetwork = false;

	/**
	 * Create a "Job" instead of just a "Pod" when launching tasks.
	 * See https://kubernetes.io/docs/concepts/workloads/controllers/jobs-run-to-completion/
	 */
	private boolean createJob = false;

	/**
	 * The node selector to use in key:value format, comma separated
	 */
	private String nodeSelector;

	/**
	 * Service account name to use for app deployments
	 */
	private String deploymentServiceAccountName;

	/**
	 * The security context to apply to created pod's.
	 */
	private PodSecurityContext podSecurityContext;

	/**
	 * The node affinity rules to apply.
	 */
	private NodeAffinity nodeAffinity;

	/**
	 * The pod affinity rules to apply
	 */
	private PodAffinity podAffinity;

	/**
	 * The pod anti-affinity rules to apply
	 */
	private PodAntiAffinity podAntiAffinity;

	/**
	 * A custom init container image name to use when creating a StatefulSet
	 */
	private String statefulSetInitContainerImageName;

	/**
	 * A custom init container to apply.
	 */
	private InitContainer initContainer;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getImagePullSecret() {
		return imagePullSecret;
	}

	public void setImagePullSecret(String imagePullSecret) {
		this.imagePullSecret = imagePullSecret;
	}

	public int getLivenessProbeDelay() {
		return livenessProbeDelay;
	}

	public void setLivenessProbeDelay(int livenessProbeDelay) {
		this.livenessProbeDelay = livenessProbeDelay;
	}

	public int getLivenessProbePeriod() {
		return livenessProbePeriod;
	}

	public void setLivenessProbePeriod(int livenessProbePeriod) {
		this.livenessProbePeriod = livenessProbePeriod;
	}

	public int getLivenessProbeTimeout() {
		return livenessProbeTimeout;
	}

	public void setLivenessProbeTimeout(int livenessProbeTimeout) {
		this.livenessProbeTimeout = livenessProbeTimeout;
	}

	public String getLivenessProbePath() {
		return livenessProbePath;
	}

	public Integer getLivenessProbePort() {
		return livenessProbePort;
	}

	public void setLivenessProbePort(Integer livenessProbePort) {
		this.livenessProbePort = livenessProbePort;
	}

	public void setLivenessProbePath(String livenessProbePath) {
		this.livenessProbePath = livenessProbePath;
	}

	public int getReadinessProbeDelay() {
		return readinessProbeDelay;
	}

	public void setReadinessProbeDelay(int readinessProbeDelay) {
		this.readinessProbeDelay = readinessProbeDelay;
	}

	public int getReadinessProbePeriod() {
		return readinessProbePeriod;
	}

	public void setReadinessProbePeriod(int readinessProbePeriod) {
		this.readinessProbePeriod = readinessProbePeriod;
	}

	public int getReadinessProbeTimeout() {
		return readinessProbeTimeout;
	}

	public void setReadinessProbeTimeout(int readinessProbeTimeout) {
		this.readinessProbeTimeout = readinessProbeTimeout;
	}

	public String getReadinessProbePath() {
		return readinessProbePath;
	}

	public void setReadinessProbePath(String readinessProbePath) {
		this.readinessProbePath = readinessProbePath;
	}

	public Integer getReadinessProbePort() {
		return readinessProbePort;
	}

	public void setReadinessProbePort(Integer readinessProbePort) {
		this.readinessProbePort = readinessProbePort;
	}

	public String getProbeCredentialsSecret() {
		return probeCredentialsSecret;
	}

	public void setProbeCredentialsSecret(String probeCredentialsSecret) {
		this.probeCredentialsSecret = probeCredentialsSecret;
	}

	public StatefulSet getStatefulSet() {
		return statefulSet;
	}

	public void setStatefulSet(
			StatefulSet statefulSet) {
		this.statefulSet = statefulSet;
	}

	public List<Toleration> getTolerations() {
		return tolerations;
	}

	public void setTolerations(List<Toleration> tolerations) {
		this.tolerations = tolerations;
	}

	public List<SecretKeyRef> getSecretKeyRefs() {
		return secretKeyRefs;
	}

	public void setSecretKeyRefs(List<SecretKeyRef> secretKeyRefs) {
		this.secretKeyRefs = secretKeyRefs;
	}

	public List<ConfigMapKeyRef> getConfigMapKeyRefs() {
		return configMapKeyRefs;
	}

	public void setConfigMapKeyRefs(List<ConfigMapKeyRef> configMapKeyRefs) {
		this.configMapKeyRefs = configMapKeyRefs;
	}

	public List<String> getConfigMapRefs() {
		return configMapRefs;
	}

	public void setConfigMapRefs(List<String> configMapRefs) {
		this.configMapRefs = configMapRefs;
	}

	public List<String> getSecretRefs() {
		return secretRefs;
	}

	public void setSecretRefs(List<String> secretRefs) {
		this.secretRefs = secretRefs;
	}

	public String[] getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(String[] environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public EntryPointStyle getEntryPointStyle() {
		return entryPointStyle;
	}

	public void setEntryPointStyle(EntryPointStyle entryPointStyle) {
		this.entryPointStyle = entryPointStyle;
	}

	public boolean isCreateLoadBalancer() {
		return createLoadBalancer;
	}

	public void setCreateLoadBalancer(boolean createLoadBalancer) {
		this.createLoadBalancer = createLoadBalancer;
	}

	public String getServiceAnnotations() {
		return serviceAnnotations;
	}

	public void setServiceAnnotations(String serviceAnnotations) {
		this.serviceAnnotations = serviceAnnotations;
	}

	public String getPodAnnotations() {
		return podAnnotations;
	}

	public void setPodAnnotations(String podAnnotations) {
		this.podAnnotations = podAnnotations;
	}

	public String getJobAnnotations() {
		return jobAnnotations;
	}

	public void setJobAnnotations(String jobAnnotations) {
		this.jobAnnotations = jobAnnotations;
	}

	public int getMinutesToWaitForLoadBalancer() {
		return minutesToWaitForLoadBalancer;
	}

	public void setMinutesToWaitForLoadBalancer(int minutesToWaitForLoadBalancer) {
		this.minutesToWaitForLoadBalancer = minutesToWaitForLoadBalancer;
	}

	public int getMaxTerminatedErrorRestarts() {
		return maxTerminatedErrorRestarts;
	}

	public void setMaxTerminatedErrorRestarts(int maxTerminatedErrorRestarts) {
		this.maxTerminatedErrorRestarts = maxTerminatedErrorRestarts;
	}

	public int getMaxCrashLoopBackOffRestarts() {
		return maxCrashLoopBackOffRestarts;
	}

	public void setMaxCrashLoopBackOffRestarts(int maxCrashLoopBackOffRestarts) {
		this.maxCrashLoopBackOffRestarts = maxCrashLoopBackOffRestarts;
	}

	public ImagePullPolicy getImagePullPolicy() {
		return imagePullPolicy;
	}

	public void setImagePullPolicy(ImagePullPolicy imagePullPolicy) {
		this.imagePullPolicy = imagePullPolicy;
	}

	public LimitsResources getLimits() {
		return limits;
	}

	public void setLimits(LimitsResources limits) {
		this.limits = limits;
	}

	public RequestsResources getRequests() {
		return requests;
	}

	public void setRequests(RequestsResources requests) {
		this.requests = requests;
	}

	public List<VolumeMount> getVolumeMounts() {
		return volumeMounts;
	}

	public void setVolumeMounts(List<VolumeMount> volumeMounts) {
		this.volumeMounts = volumeMounts;
	}

	public List<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}

	public boolean isHostNetwork() {
		return hostNetwork;
	}

	public void setHostNetwork(boolean hostNetwork) {
		this.hostNetwork = hostNetwork;
	}

	public boolean isCreateJob() {
		return createJob;
	}

	public void setCreateJob(boolean createJob) {
		this.createJob = createJob;
	}

	public String getDeploymentServiceAccountName() {
		return deploymentServiceAccountName;
	}

	public void setDeploymentServiceAccountName(String deploymentServiceAccountName) {
		this.deploymentServiceAccountName = deploymentServiceAccountName;
	}

	public int getMaximumConcurrentTasks() {
		return maximumConcurrentTasks;
	}

	public void setMaximumConcurrentTasks(int maximumConcurrentTasks) {
		this.maximumConcurrentTasks = maximumConcurrentTasks;
	}

	public void setNodeSelector(String nodeSelector) {
		this.nodeSelector = nodeSelector;
	}

	public String getNodeSelector() {
		return nodeSelector;
	}

	public void setPodSecurityContext(PodSecurityContext podSecurityContext) {
		this.podSecurityContext = podSecurityContext;
	}

	public PodSecurityContext getPodSecurityContext() {
		return podSecurityContext;
	}

	public NodeAffinity getNodeAffinity() {
		return nodeAffinity;
	}

	public void setNodeAffinity(NodeAffinity nodeAffinity) {
		this.nodeAffinity = nodeAffinity;
	}

	public PodAffinity getPodAffinity() {
		return podAffinity;
	}

	public void setPodAffinity(PodAffinity podAffinity) {
		this.podAffinity = podAffinity;
	}

	public PodAntiAffinity getPodAntiAffinity() {
		return podAntiAffinity;
	}

	public void setPodAntiAffinity(PodAntiAffinity podAntiAffinity) {
		this.podAntiAffinity = podAntiAffinity;
	}

	public String getStatefulSetInitContainerImageName() {
		return statefulSetInitContainerImageName;
	}

	public void setStatefulSetInitContainerImageName(String statefulSetInitContainerImageName) {
		this.statefulSetInitContainerImageName = statefulSetInitContainerImageName;
	}

	public InitContainer getInitContainer() {
		return initContainer;
	}

	public void setInitContainer(InitContainer initContainer) {
		this.initContainer = initContainer;
	}
}
