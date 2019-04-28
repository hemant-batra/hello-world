package app.jpa.converters;

import app.jpa.dtos.UserDTO;
import app.jpa.entities.User;
import app.jpa.repositories.UsersRepository;
import app.utilities.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends EntityConverter<String, User, UserDTO> {

    private final UsersRepository usersRepository;

    @Autowired
    public UserConverter(UsersRepository usersRepository) {
        super(usersRepository, User.class, UserDTO.class);
        this.usersRepository = usersRepository;
    }
}