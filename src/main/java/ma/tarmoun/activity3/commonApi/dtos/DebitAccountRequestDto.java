package ma.tarmoun.activity3.commonApi.dtos;

import lombok.Data;

@Data
public class DebitAccountRequestDto {
    private String id;

    private double amount ;
    private String currency ;


}
