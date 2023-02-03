package com.enrealit.ppj.apiserver.security;

import java.time.LocalDate;
import org.springframework.security.core.context.SecurityContextHolder;
import com.enrealit.ppj.apiserver.exception.UserNotAllowedException;
import com.enrealit.ppj.shared.dto.user.ProfileDto;
import com.enrealit.ppj.shared.enums.ProfileType;
import com.enrealit.ppj.shared.security.UserPrincipal;

public class SecurityHelper {

  private SecurityHelper() {}

  public static String getCurrentUserId() {
    return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUser().getId();
  }

  public static ProfileType getCurrentProfile() {
    UserPrincipal userPrincipal =
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    if (userPrincipal == null || userPrincipal.getUser() == null) {
      throw new UserNotAllowedException("User not allowed");
    }
    ProfileDto userProfile = userPrincipal.getUser().getProfile();
    return  userProfile.getEndDate().isBefore(LocalDate.now()) ? ProfileType.PRO : userProfile.getType();
  }

}
