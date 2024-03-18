package devx.challenge.login.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  private String email;
  private String password;
  private boolean isMfaEnabled = false;
  private String mfaCode;
  private boolean isLastMfaAvailable;
  private LocalDateTime lastLogin;

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public boolean isLastMfaAvailable() {
    return isLastMfaAvailable;
  }

  public void setLastMfaAvailable(boolean lastMfaAvailable) {
    isLastMfaAvailable = lastMfaAvailable;
  }

  public String getMfaCode() {
    return mfaCode;
  }

  public void setMfaCode(String mfaCode) {
    this.mfaCode = mfaCode;
  }

  public boolean isMfaEnabled() {
    return isMfaEnabled;
  }

  public void setMfaEnabled(boolean mfaEnabled) {
    isMfaEnabled = mfaEnabled;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
