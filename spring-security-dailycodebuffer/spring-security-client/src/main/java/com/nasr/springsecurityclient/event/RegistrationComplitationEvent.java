package com.nasr.springsecurityclient.event;

import com.nasr.springsecurityclient.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationComplitationEvent {

    private User user;
    private String url;

}
