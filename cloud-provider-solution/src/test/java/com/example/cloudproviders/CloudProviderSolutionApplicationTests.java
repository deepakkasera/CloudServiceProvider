package com.example.cloudproviders;

import com.example.cloudproviders.adapters.CloudAdapter;
import com.example.cloudproviders.controllers.CloudController;
import com.example.cloudproviders.dtos.CreateConnectionRequestDto;
import com.example.cloudproviders.dtos.CreateConnectionResponseDto;
import com.example.cloudproviders.dtos.ResponseStatus;
import com.example.cloudproviders.libraries.aws.AWSApi;
import com.example.cloudproviders.libraries.google.GoogleApi;
import com.example.cloudproviders.models.User;
import com.example.cloudproviders.repositories.UserRepository;
import com.example.cloudproviders.services.CloudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CloudProviderSolutionApplicationTests {
    private CloudController cloudControllerWithAWSAdapter;
    private CloudController cloudControllerWithGoogleAdapter;
    private CloudService cloudServiceWithAWSAdapter;
    private CloudService cloudServiceWithGoogleAdapter;
    private UserRepository userRepository;
    private CloudAdapter awsAdapterImpl;
    private CloudAdapter googleAdapterImpl;

    @BeforeEach
    public void setup() throws Exception {
        initializeComponents();
    }

    private void initializeComponents() throws Exception {
        initializeRepositories();
        initializeAdapters();
        initializeCloudService();
        initializeCloudController();
    }

    private <T> T createInstance(Class<T> interfaceClass, Reflections reflections) throws Exception {
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceClass);
        if (implementations.isEmpty()) {
            throw new Exception("No implementation for " + interfaceClass.getSimpleName() + " found");
        }
        Class<? extends T> implementationClass = implementations.iterator().next();
        return getT(implementationClass);
    }

    private static <T> T getT(Class<? extends T> implementationClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
        Constructor<? extends T> constructor = implementationClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private <T> T createInstanceWithArgs(Class<T> interfaceClass, Reflections reflections, List<Object> dependencies) throws Exception {
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceClass);
        if (implementations.isEmpty()) {
            throw new Exception("No implementation for " + interfaceClass.getSimpleName() + " found");
        }
        Class<? extends T> implementationClass = implementations.iterator().next();
        Constructor<?>[] constructors = implementationClass.getConstructors();
        Constructor<?> constructor = Arrays.stream(constructors)
                .filter(constructor1 -> constructor1.getParameterCount() == dependencies.size())
                .findFirst().orElseThrow(() -> new Exception("No constructor with " + dependencies.size() + " arguments found"));
        constructor.setAccessible(true);
        Object[] args = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            for (Object dependency : dependencies) {
                if (constructor.getParameterTypes()[i].isInstance(dependency)) {
                    args[i] = dependency;
                    break;
                }
            }
        }
        return (T) constructor.newInstance(args);
    }

    private void initializeRepositories() throws Exception {
        Reflections repositoryReflections = new Reflections(UserRepository.class.getPackageName(),
                new SubTypesScanner(false));
        this.userRepository = createInstance(UserRepository.class, repositoryReflections);
    }

    private void initializeAdapters() throws Exception {
        Reflections adapterReflections = new Reflections(CloudAdapter.class.getPackageName(),
                new SubTypesScanner(false));

        Set<Class<? extends CloudAdapter>> implementations = adapterReflections.getSubTypesOf(CloudAdapter.class);
        if (implementations.isEmpty()) {
            throw new Exception("No implementation for " + CloudAdapter.class.getSimpleName() + " found");
        }
        if(implementations.size() != 2){
            throw new Exception("There should be exactly 2 implementation for" + CloudAdapter.class.getSimpleName() + " interface, 1 for AWS and 1 for Google");
        }
        Optional<Class<? extends CloudAdapter>> awsAdapterOptional = implementations.stream().filter(implementation ->
                Arrays.stream(implementation.getDeclaredFields()).anyMatch(field -> field.getType().equals(AWSApi.class))).findFirst();

        if (awsAdapterOptional.isEmpty()) {
            throw new Exception("No adapter found for AWSAPI");
        }

        Optional<Class<? extends CloudAdapter>> googleAdapterOptional = implementations.stream().filter(implementation ->
                Arrays.stream(implementation.getDeclaredFields()).anyMatch(field -> field.getType().equals(GoogleApi.class))).findFirst();
        if(googleAdapterOptional.isEmpty()){
            throw new Exception("No adapter found for GoogleAPI");
        }

        Class<? extends CloudAdapter> awsAdapterClass = awsAdapterOptional.get();
        this.awsAdapterImpl = getT(awsAdapterClass);
        Class<? extends CloudAdapter> googleAdapterClass = googleAdapterOptional.get();
        this.googleAdapterImpl = getT(googleAdapterClass);
    }

    private void initializeCloudService() throws Exception {
        Reflections serviceReflections = new Reflections(CloudService.class.getPackageName(), new SubTypesScanner(false));
        this.cloudServiceWithAWSAdapter = createInstanceWithArgs(CloudService.class, serviceReflections, Arrays.asList(this.userRepository,
                this.awsAdapterImpl));

        this.cloudServiceWithGoogleAdapter = createInstanceWithArgs(CloudService.class, serviceReflections, Arrays.asList(this.userRepository,
                this.googleAdapterImpl));
    }

    private void initializeCloudController() throws Exception {
        this.cloudControllerWithAWSAdapter = new CloudController(this.cloudServiceWithAWSAdapter);
        this.cloudControllerWithGoogleAdapter = new CloudController(this.cloudServiceWithGoogleAdapter);
    }

    @Test
    public void testCreateConnectionWithAwsAdapterAndValidUserId() {
        User user = new User();
        user.setName("TestUser1");
        user.setPassword("password");
        user = this.userRepository.save(user);

        CreateConnectionRequestDto requestDto = new CreateConnectionRequestDto();
        requestDto.setUserId(user.getId());

        CreateConnectionResponseDto responseDto = this.cloudControllerWithAWSAdapter.createConnection(requestDto);
        assertNotNull(responseDto, "CreateConnectionResponseDto shouldn't be NULL");
        assertNotNull(responseDto.getConnectionId(), "Connection Id shouldn't be NULL");
        assertNotNull(responseDto.getConnectionStatus(), "Connection status shouldn't be NULL");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus());
    }

    @Test
    public void testCreateConnectionWithAwsAdapterAndInValidUserId() {
        User user = new User();
        user.setName("TestUser2");
        user.setPassword("password");
        user = this.userRepository.save(user);

        CreateConnectionRequestDto requestDto = new CreateConnectionRequestDto();
        requestDto.setUserId(user.getId() + 10);

        CreateConnectionResponseDto responseDto = this.cloudControllerWithAWSAdapter.createConnection(requestDto);
        assertNotNull(responseDto, "CreateConnectionResponseDto shouldn't be NULL");
        assertNull(responseDto.getConnectionId(), "Connection Id should be NULL");
        assertNull(responseDto.getConnectionStatus(), "Connection status should be NULL");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus());
    }

    @Test
    public void testCreateConnectionWithGoogleAdapterAndValidUserId() {
        User user = new User();
        user.setName("TestUser1");
        user.setPassword("password");
        user = this.userRepository.save(user);

        CreateConnectionRequestDto requestDto = new CreateConnectionRequestDto();
        requestDto.setUserId(user.getId());

        CreateConnectionResponseDto responseDto = this.cloudControllerWithGoogleAdapter.createConnection(requestDto);
        assertNotNull(responseDto, "CreateConnectionResponseDto shouldn't be NULL");
        assertNotNull(responseDto.getConnectionId(), "Connection Id shouldn't be NULL");
        assertNotNull(responseDto.getConnectionStatus(), "Connection status shouldn't be NULL");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponseStatus());
    }

    @Test
    public void testCreateConnectionWithGoogleAdapterAndInValidUserId() {
        User user = new User();
        user.setName("TestUser2");
        user.setPassword("password");
        user = this.userRepository.save(user);

        CreateConnectionRequestDto requestDto = new CreateConnectionRequestDto();
        requestDto.setUserId(user.getId() + 10);

        CreateConnectionResponseDto responseDto = this.cloudControllerWithGoogleAdapter.createConnection(requestDto);
        assertNotNull(responseDto, "CreateConnectionResponseDto shouldn't be NULL");
        assertNull(responseDto.getConnectionId(), "Connection Id should be NULL");
        assertNull(responseDto.getConnectionStatus(), "Connection status should be NULL");
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponseStatus());
    }

}
