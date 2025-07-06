package fr.ecoride.config.security;

import fr.ecoride.model.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UtilisateurDetails implements UserDetails {

    private final Utilisateur utilisateur;

    public UtilisateurDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword(); // doit retourner le hash
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail(); // ce que tu passes à UsernamePasswordAuthenticationToken
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utilisateur.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getLibelle().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !utilisateur.isSuspendu(); // suspendu = utilisateur bloqué
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}
