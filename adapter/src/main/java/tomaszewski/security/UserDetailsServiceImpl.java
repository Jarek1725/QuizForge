package tomaszewski.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tomaszewski.out.repositories.JpaUserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserSecurityDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return
                jpaUserRepository.findByEmailAndRoles(email)
                        .map(UserSecurityDetails::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}
