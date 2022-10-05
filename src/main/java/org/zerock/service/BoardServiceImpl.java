package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {

		log.info("register......" + board);
		//게시글 테이블에 새글 정보를 저장 - 1. 글번호를 nextVal로 추출하여 사용 - 새로 저장된 글번호를 매개변수 사용한 BoardVO 객체에 담는다.
		mapper.insertSelectKey(board);
		//업로드된 사진 파일이 있는지 체크
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}

		board.getAttachList().forEach(attach -> {

			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {

		log.info("get......" + bno);

		return mapper.read(bno);

	}

	@Transactional
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);

		attachMapper.deleteAll(board.getBno());

		boolean modifyResult = mapper.update(board) == 1;
		
		if (modifyResult && board.getAttachList().size() > 0) {

			board.getAttachList().forEach(attach -> {

				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}

	// @Override
	// public boolean modify(BoardVO board) {
	//
	// log.info("modify......" + board);
	//
	// return mapper.update(board) == 1;
	// }

	// @Override
	// public boolean remove(Long bno) {
	//
	// log.info("remove...." + bno);
	//
	// return mapper.delete(bno) == 1;
	// }

	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);

		attachMapper.deleteAll(bno);

		return mapper.delete(bno) == 1;
	}

	// @Override
	// public List<BoardVO> getList() {
	//
	// log.info("getList..........");
	//
	// return mapper.getList();
	// }

	@Override
	public List<BoardVO> getList(Criteria cri) {

		log.info("get List with criteria: " + cri);

		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}

//	@Override
//	public void removeAttach(Long bno) {
//
//		log.info("remove all attach files");
//
//		attachMapper.deleteAll(bno);
//	}

}
