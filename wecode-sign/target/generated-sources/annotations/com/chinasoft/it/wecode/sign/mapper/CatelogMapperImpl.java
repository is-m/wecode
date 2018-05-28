package com.chinasoft.it.wecode.sign.mapper;

import com.chinasoft.it.wecode.sign.domain.Catelog;
import com.chinasoft.it.wecode.sign.dto.CatelogDto;
import com.chinasoft.it.wecode.sign.dto.CatelogResultDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-05-23T07:37:34+0800",
    comments = "version: 1.2.0.CR1, compiler: javac, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class CatelogMapperImpl implements CatelogMapper {

    @Override
    public CatelogResultDto from(Catelog arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CatelogResultDto catelogResultDto = new CatelogResultDto();

        catelogResultDto.setPid( arg0.getPid() );
        catelogResultDto.setName( arg0.getName() );
        catelogResultDto.setPath( arg0.getPath() );
        catelogResultDto.setFullPath( arg0.getFullPath() );
        catelogResultDto.setIcon( arg0.getIcon() );
        catelogResultDto.setStatus( arg0.getStatus() );
        catelogResultDto.setSeq( arg0.getSeq() );
        catelogResultDto.setId( arg0.getId() );
        catelogResultDto.setAllowType( arg0.getAllowType() );
        catelogResultDto.setAllowValue( arg0.getAllowValue() );

        return catelogResultDto;
    }

    @Override
    public List<CatelogResultDto> toDtoList(List<Catelog> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<CatelogResultDto> list = new ArrayList<CatelogResultDto>( arg0.size() );
        for ( Catelog catelog : arg0 ) {
            list.add( from( catelog ) );
        }

        return list;
    }

    @Override
    public Catelog to(CatelogDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Catelog catelog = new Catelog();

        catelog.setName( arg0.getName() );
        catelog.setPath( arg0.getPath() );
        catelog.setIcon( arg0.getIcon() );
        catelog.setStatus( arg0.getStatus() );
        catelog.setSeq( arg0.getSeq() );
        catelog.setPid( arg0.getPid() );
        catelog.setAllowType( arg0.getAllowType() );
        catelog.setAllowValue( arg0.getAllowValue() );
        catelog.setTarget( arg0.getTarget() );

        return catelog;
    }

    @Override
    public List<Catelog> toEntities(List<CatelogResultDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Catelog> list = new ArrayList<Catelog>( arg0.size() );
        for ( CatelogResultDto catelogResultDto : arg0 ) {
            list.add( catelogResultDtoToCatelog( catelogResultDto ) );
        }

        return list;
    }

    @Override
    public List<Catelog> toEntityList(List<CatelogDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Catelog> list = new ArrayList<Catelog>( arg0.size() );
        for ( CatelogDto catelogDto : arg0 ) {
            list.add( to( catelogDto ) );
        }

        return list;
    }

    @Override
    public List<CatelogResultDto> from(List<Catelog> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CatelogResultDto> list = new ArrayList<CatelogResultDto>( entities.size() );
        for ( Catelog catelog : entities ) {
            list.add( from( catelog ) );
        }

        return list;
    }

    protected Catelog catelogResultDtoToCatelog(CatelogResultDto catelogResultDto) {
        if ( catelogResultDto == null ) {
            return null;
        }

        Catelog catelog = new Catelog();

        catelog.setId( catelogResultDto.getId() );
        catelog.setName( catelogResultDto.getName() );
        catelog.setPath( catelogResultDto.getPath() );
        catelog.setFullPath( catelogResultDto.getFullPath() );
        catelog.setIcon( catelogResultDto.getIcon() );
        catelog.setStatus( catelogResultDto.getStatus() );
        catelog.setSeq( catelogResultDto.getSeq() );
        catelog.setPid( catelogResultDto.getPid() );
        catelog.setAllowType( catelogResultDto.getAllowType() );
        catelog.setAllowValue( catelogResultDto.getAllowValue() );

        return catelog;
    }
}
