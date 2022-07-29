package kr.co.pincar.mybatis.mapper;

import kr.co.pincar.mybatis.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardDto> findAll();

    BoardDto findById(Long id);

    BoardDto findByWriter(String writer);

    int save(BoardDto board);
}
