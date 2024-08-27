package com.shepherd.E_commerce.repository;



import com.shepherd.E_commerce.models.Products;
import com.shepherd.E_commerce.models.Roles;
import com.shepherd.E_commerce.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;


    @Test
    void givenNewUser_whenSave_thenSuccess(){

        //given
        User user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();
        User insertedUser = userRepository.save(user);
        Assertions.assertThat(insertedUser).isNotNull();
        Assertions.assertThat(insertedUser.getId()).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(insertedUser.getId());

    }

    @Test
    void shouldReturnTrue_whenExistUserByEmail(){
        User user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();

        User insertedUser = userRepository.save(user);
        Boolean existUser = userRepository.existsByEmail(insertedUser.getEmail());
        Assertions.assertThat(existUser).isTrue();
    }

    @Test
    void shouldReturnUser_whenFindByEmail(){
        User user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();
        userRepository.save(user);
        User existUser = userRepository.findByEmail("user@example.com").get();
        Assertions.assertThat(existUser).isNotNull();
    }

    @Test
    void shouldReturnUser_whenGetReferenceByEmail(){
        User user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();
        userRepository.save(user);
        User existUser = userRepository.getReferenceByEmail("user@example.com");
        Assertions.assertThat(existUser).isNotNull();

    }

    @Test
    void testFindAllProduct(){

        List<User> userList = userRepository.findAll();

        Assertions.assertThat(userList).hasSizeGreaterThan(0);
    }

    @Test
    void testDeleteById(){

        User user = User.builder()
                .username("exampleUser")
                .password("password")
                .email("user@example.com")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Roles.ROLE_USER))  // Assign the ROLE_USER to this user
                .build();
        userRepository.save(user);

        // Delete the product by its ID
        userRepository.deleteById(user.getId());

        // Verify that deleteById was called once with the correct ID
        Optional<User> deletedUser = userRepository.findById(user.getId());

        Assertions.assertThat(deletedUser).isEmpty();

    }
}
