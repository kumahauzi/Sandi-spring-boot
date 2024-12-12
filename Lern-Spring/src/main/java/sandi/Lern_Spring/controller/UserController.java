package sandi.Lern_Spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sandi.Lern_Spring.entity.UserEntity;
import sandi.Lern_Spring.repositoy.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/m_user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/findAll")
    public ResponseEntity<List<UserEntity>> getAllUserr() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return new ResponseEntity<>(userEntityList, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("userr", new UserEntity());
        return "user-form"; // View untuk form input seller baru
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserEntity updatedUser) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setUserName(updatedUser.getUserName());
            user.setUserEmail(updatedUser.getUserEmail());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
