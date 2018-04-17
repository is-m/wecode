package com.chinasoft.it.wecode.sign.mapper;

import com.chinasoft.it.wecode.sign.domain.SignLog;
import com.chinasoft.it.wecode.sign.dto.SignLogDto;
import com.chinasoft.it.wecode.sign.dto.SignLogResultDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-17T07:10:51+0800",
    comments = "version: 1.2.0.CR1, compiler: javac, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class SignLogMapperImpl implements SignLogMapper {

    @Override
    public SignLogResultDto from(SignLog arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SignLogResultDto signLogResultDto = new SignLogResultDto();

        signLogResultDto.setUserId( arg0.getUserId() );
        signLogResultDto.setSignTime( arg0.getSignTime() );
        signLogResultDto.setSignPoint( arg0.getSignPoint() );
        signLogResultDto.setId( arg0.getId() );

        return signLogResultDto;
    }

    @Override
    public SignLog to(SignLogDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SignLog signLog = new SignLog();

        signLog.setUserId( arg0.getUserId() );
        signLog.setSignTime( arg0.getSignTime() );
        signLog.setSignPoint( arg0.getSignPoint() );

        return signLog;
    }
}
