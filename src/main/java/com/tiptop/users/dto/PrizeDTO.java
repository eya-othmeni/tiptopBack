package com.tiptop.users.dto;

import com.tiptop.users.entities.PRIZE;
import com.tiptop.users.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrizeDTO {
    private String description;
    private int angle;
    UserDTO user;
    public PrizeDTO(PRIZE prize){
        this.description = prize.prize;
    }
    public PrizeDTO(PRIZE prize, User user,int angle){
        this.user = new UserDTO(user);
        this.description = prize.prize;
        this.angle=angle;
    }

    public PrizeDTO(){

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
