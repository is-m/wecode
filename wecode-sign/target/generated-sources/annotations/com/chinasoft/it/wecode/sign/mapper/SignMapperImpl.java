package com.chinasoft.it.wecode.sign.mapper;

import com.chinasoft.it.wecode.sign.domain.Sign;
import com.chinasoft.it.wecode.sign.dto.SignDto;
import com.chinasoft.it.wecode.sign.dto.SignResultDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-05T22:07:43+0800",
    comments = "version: 1.2.0.CR1, compiler: javac, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class SignMapperImpl implements SignMapper {

    @Override
    public SignResultDto from(Sign arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SignResultDto signResultDto = new SignResultDto();

        signResultDto.setUserId( arg0.getUserId() );
        signResultDto.setSignDate( arg0.getSignDate() );
        signResultDto.setBeginTime( arg0.getBeginTime() );
        signResultDto.setEndTime( arg0.getEndTime() );

        return signResultDto;
    }

    @Override
    public Sign to(SignDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Sign sign = new Sign();

        sign.setUserId( arg0.getUserId() );

        return sign;
    }
}
