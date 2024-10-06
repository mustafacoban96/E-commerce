package com.shepherd.E_commerce.service;


import com.shepherd.E_commerce.dto.requests.CreateUserRequest;
import com.shepherd.E_commerce.dto.requests.UpdateUserRequest;
import com.shepherd.E_commerce.dto.response.GetUserByIdResponse;
import com.shepherd.E_commerce.dto.response.UserListResponse;
import com.shepherd.E_commerce.dto.response.UserResponse;
import com.shepherd.E_commerce.dto.response.UserUpdateResponse;
import com.shepherd.E_commerce.exceptions.EmailAlreadyExistsException;
import com.shepherd.E_commerce.exceptions.PasswordMismatchException;
import com.shepherd.E_commerce.exceptions.UserNotFoundException;
import com.shepherd.E_commerce.mappers.UserMapper;
import com.shepherd.E_commerce.models.Cart;
import com.shepherd.E_commerce.models.Roles;
import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.repository.UserRepository;
import com.shepherd.E_commerce.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private CartService cartService;


    private UserServiceImpl userService;

    @BeforeEach
    void setUp(){

        userRepository = Mockito.mock(UserRepository.class);
        userMapper = Mockito.mock(UserMapper.class);
        cartService = Mockito.mock(CartService.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        userService = new UserServiceImpl(
                userRepository,
                userMapper,
                passwordEncoder,
                cartService
                );

    }

    @Test
    void whenLoadUserByName_ByUsingEmail_shouldReturnUser(){
        String email = "mus@gmail.com";
        User user = User.builder()
                .username("aaa")
                .email("mus@gmail.com")
                .password(passwordEncoder.encode("123"))
                .authorities(Set.of(Roles.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User actualUser = userService.loadUserByUsername(email);

        Assertions.assertEquals(user,actualUser);

    }
    @Test
    void whenLoadUserByName_ByUsingEmail_shouldThrowUsernameNotFoundException(){
        String email = "mus@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,()->
                userService.loadUserByUsername(email));
    }

    @Test
    void whenCreateUser_withValidCreateUserRequest_shouldReturnUser(){
        //Arrange
        CreateUserRequest request = CreateUserRequest.builder()
                .username("aaaa")
                .email("aa@gmail.com")
                .password("123")
                .confirm_password("123")
                .authorities(Set.of(Roles.ROLE_USER))
                .build();
        User expectedUser = User.builder()
                .username("aaa")
                .email("aa@gmail.com")
                .password(passwordEncoder.encode("123"))
                .authorities(Set.of(Roles.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        Cart cart = Cart.builder()
                .user(expectedUser)
                .build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUser);
        when(cartService.newCart(expectedUser)).thenReturn(cart);

        //Act
        User actualUser = userService.createUser(request);

        //Assert
        Assertions.assertEquals(expectedUser,actualUser);


    }

    @Test
    void whenCreateUser_shouldThrowPasswordMismatchException(){
        CreateUserRequest request = CreateUserRequest.builder()
                .username("aaaa")
                .email("aa@gmail.com")
                .password("1234")
                .confirm_password("123")
                .authorities(Set.of(Roles.ROLE_USER))
                .build();

        assertThrows(PasswordMismatchException.class,()->
                userService.createUser(request));
    }

    @Test
    void whenCreateUser_shouldThrowEmailAlreadyExistsException(){
        // Arrange: Set up the CreateUserRequest and mock the userRepository
        CreateUserRequest request = CreateUserRequest.builder()
                .username("aaaa")
                .email("aa@gmail.com")
                .password("123")
                .confirm_password("123")
                .authorities(Set.of(Roles.ROLE_USER))
                .build();

        // Mock the repository behavior to simulate the existing email
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        // Act & Assert: Verify that the exception is thrown
        assertThrows(EmailAlreadyExistsException.class, () ->
                userService.createUser(request));
    }
    @Test
    void getAllUsers_shouldReturnListUserListResponse(){
        User user1 = User.builder()
                .username("aaa")
                .email("aa@gmail.com")
                .password(passwordEncoder.encode("123"))
                .authorities(Set.of(Roles.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        User user2 = User.builder()
                .username("aaa")
                .email("aa2@gmail.com")
                .password(passwordEncoder.encode("123"))
                .authorities(Set.of(Roles.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        when(userMapper.UserEntityToResponse(user1)).thenReturn(
                new UserListResponse(user1.getId(),user1.getUsername(), user1.getEmail()));
        when(userMapper.UserEntityToResponse(user2)).thenReturn(
                new UserListResponse(user2.getId(),user2.getUsername(), user2.getEmail()));
        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        List<UserListResponse> userList= userService.getAllUsers();

        org.assertj.core.api.Assertions.assertThat(userList).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userList).hasSize(2);

    }

    @Test
    void shouldThrowUserNotFoundException_whenDeleteUserById(){
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->userService.deleteUserById(id));

    }

    @Test
    void test_deleteUserById(){
        User user = User.builder()
                .username("aaa")
                .email("aa@gmail.com")
                .password(passwordEncoder.encode("123"))
                .authorities(Set.of(Roles.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUserById(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());

        org.assertj.core.api.Assertions.assertThat(deletedUser).isEmpty();
    }

    // Test: User not found
    @Test
    void updateUserById_shouldThrowUserNotFoundException_whenUserNotExists() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("oldPassword")
                .build();
        when(userRepository.existsById(userId)).thenReturn(false);

        UpdateUserRequest request = new UpdateUserRequest("newUsername", "new@example.com", "newPassword", "newPassword");

        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(userId, request));

        verify(userRepository, never()).getReferenceById(any());
    }

    @Test
    void updateUserById_shouldThrowPasswordMismatchException_whenPasswordsDoNotMatch() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("oldPassword")
                .build();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        UpdateUserRequest request = new UpdateUserRequest("newUsername", "new@example.com", "newPassword1", "newPassword2");

        assertThrows(PasswordMismatchException.class, () -> userService.updateUserById(userId, request));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserById_shouldUpdateUser_whenNoPasswordChange() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("oldPassword")
                .build();

        // Mocking repository responses
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // Creating the request with new username and email
        UpdateUserRequest request = new UpdateUserRequest("newUsername", "new@example.com", null, null);

        // Mocking the mapper response
        UserUpdateResponse response = new UserUpdateResponse(user.getId(), "new@example.com", "newUsername");
        when(userMapper.UserEntityToUpdateResponse(any(User.class))).thenReturn(response);

        // Calling the service method
        UserUpdateResponse updatedResponse = userService.updateUserById(userId, request);

        // Verifying that the user was updated correctly
        assertThat(updatedResponse).isNotNull();
        assertThat(updatedResponse.username()).isEqualTo("newUsername");
        assertThat(updatedResponse.email()).isEqualTo("new@example.com");

        // Verifying the changes made in the user object
        assertThat(user.getUsername()).isEqualTo("newUsername");
        assertThat(user.getEmail()).isEqualTo("new@example.com");

        // Verify that the save method was called on the repository
        verify(userRepository).save(any(User.class));
    }

    // Test: Successful update with password change
    @Test
    void updateUserById_shouldUpdateUser_whenPasswordChangeIsValid() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("oldPassword")
                .build();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        UpdateUserRequest request = new UpdateUserRequest("newUsername", "new@example.com", "newPassword", "newPassword");
        UserUpdateResponse response = new UserUpdateResponse(user.getId(), "new@example.com", "newUsername");
        when(userMapper.UserEntityToUpdateResponse(any(User.class))).thenReturn(response);


        UserUpdateResponse updatedResponse = userService.updateUserById(userId, request);

        assertThat(updatedResponse).isNotNull();
        assertThat(updatedResponse.username()).isEqualTo("newUsername");
        assertThat(updatedResponse.email()).isEqualTo("new@example.com");

        verify(userRepository).save(any(User.class));
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("password")
                .build();
        GetUserByIdResponse response = new GetUserByIdResponse(userId,"existing@example.com", "existingUser");

        // Mocking repository and mapper responses
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(userMapper.UserEntityToResponseById(user)).thenReturn(response);

        // Calling the service method
        GetUserByIdResponse returnedResponse = userService.getUserById(userId);

        // Verifying the response
        assertThat(returnedResponse).isNotNull();
        assertThat(returnedResponse.username()).isEqualTo("existingUser");
        assertThat(returnedResponse.email()).isEqualTo("existing@example.com");

        // Verify the interactions
        verify(userRepository).existsById(userId);
        verify(userRepository).getReferenceById(userId);
        verify(userMapper).UserEntityToResponseById(user);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        // Mocking repository response
        when(userRepository.existsById(userId)).thenReturn(false);

        // Verifying that the exception is thrown
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        // Verify that getReferenceById is never called
        verify(userRepository, never()).getReferenceById(any(UUID.class));
        verify(userMapper, never()).UserEntityToResponseById(any(User.class));
    }

    @Test
    void getByEmail_shouldReturnUser_whenUserExists() {
        String email = "existing@example.com";
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("existingUser")
                .email(email)
                .password("password")
                .authorities(Set.of(Roles.ROLE_USER))
                .build();
        UserResponse response = new UserResponse(user.getId(),"existingUser",email,user.getAuthorities());

        // Mocking repository and mapper responses
        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.getReferenceByEmail(email)).thenReturn(user);
        when(userMapper.UserEntityToResponseByEmail(user)).thenReturn(response);

        // Calling the service method
        UserResponse returnedResponse = userService.getByEmail(email);

        // Verifying the response
        assertThat(returnedResponse).isNotNull();
        assertThat(returnedResponse.username()).isEqualTo("existingUser");
        assertThat(returnedResponse.email()).isEqualTo(email);

        // Verify the interactions
        verify(userRepository).existsByEmail(email);
        verify(userRepository).getReferenceByEmail(email);
        verify(userMapper).UserEntityToResponseByEmail(user);
    }

    @Test
    void getByEmail_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        String email = "nonexistent@example.com";

        // Mocking repository response
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // Verifying that the exception is thrown
        assertThrows(UserNotFoundException.class, () -> userService.getByEmail(email));

        // Verify that getReferenceByEmail is never called
        verify(userRepository, never()).getReferenceByEmail(anyString());
        verify(userMapper, never()).UserEntityToResponseByEmail(any(User.class));
    }

    @Test
    void getUserByIdAsEntity_shouldReturnUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .username("existingUser")
                .email("existing@example.com")
                .password("password")
                .build();

        // Mocking repository response
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // Calling the service method
        User returnedUser = userService.getUserByIdAsEntity(userId);

        // Verifying the response
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getId()).isEqualTo(userId);
        assertThat(returnedUser.getUsername()).isEqualTo("existingUser");

        // Verify the interactions
        verify(userRepository).existsById(userId);
        verify(userRepository).getReferenceById(userId);
    }


    @Test
    void getUserByIdAsEntity_shouldThrowUsernameNotFoundException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        // Mocking repository response
        when(userRepository.existsById(userId)).thenReturn(false);

        // Verifying that the exception is thrown
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByIdAsEntity(userId));

        // Verify that getReferenceById is never called
        verify(userRepository, never()).getReferenceById(any(UUID.class));
    }

    @Test
    void getUserByEmailAsEntity_shouldReturnUser_whenUserExists() {
        String email = "existing@example.com";
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("existingUser")
                .email(email)
                .password("password")
                .build();

        // Mocking repository response
        when(userRepository.getReferenceByEmail(email)).thenReturn(user);

        // Calling the service method
        User returnedUser = userService.getUserByEmailAsEntity(email);

        // Verifying the response
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(email);
        assertThat(returnedUser.getUsername()).isEqualTo("existingUser");

        // Verify the interaction
        verify(userRepository).getReferenceByEmail(email);
    }




}
