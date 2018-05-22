package com.chinasoft.it.wecode.sign.mapper;

import com.chinasoft.it.wecode.sign.domain.SignLog;
import com.chinasoft.it.wecode.sign.dto.SignLogDto;
import com.chinasoft.it.wecode.sign.dto.SignLogResultDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-05-21T23:34:14+0800",
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
    public List<SignLogResultDto> toDtoList(List<SignLog> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SignLogResultDto> list = new ArrayList<SignLogResultDto>( arg0.size() );
        for ( SignLog signLog : arg0 ) {
            list.add( from( signLog ) );
        }

        return list;
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

    @Override
    public List<SignLog> toEntities(List<SignLogResultDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SignLog> list = new ArrayList<SignLog>( arg0.size() );
        for ( SignLogResultDto signLogResultDto : arg0 ) {
            list.add( to( signLogResultDto ) );
        }

        return list;
    }

    @Override
    public List<SignLog> toEntityList(List<SignLogDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SignLog> list = new ArrayList<SignLog>( arg0.size() );
        for ( SignLogDto signLogDto : arg0 ) {
            list.add( to( signLogDto ) );
        }

        return list;
    }
}
