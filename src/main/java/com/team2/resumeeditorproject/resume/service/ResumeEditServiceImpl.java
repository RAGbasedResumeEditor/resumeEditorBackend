package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * resumeEditServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeEditServiceImpl
 * @since : 04/25/24
 */
@Service
public class ResumeEditServiceImpl implements ResumeEditService{
    @Autowired
    private ResumeEditRepository resumeEditRepository;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ResumeBoardService resumeBoardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResumeEditDTO insertResumeEdit(ResumeEditDTO resumeEditDTO) {
        ResumeEdit resumeEdit = modelMapper.map(resumeEditDTO, ResumeEdit.class);
        ResumeEdit savedResume = resumeEditRepository.save(resumeEdit);
        return modelMapper.map(savedResume, ResumeEditDTO.class);
    }

    @Transactional
    @Override
    public ResumeEditDTO insertResumeEditTransaction(ResumeEditDTO resumeEditDTO, String content, User user, int mode) throws Exception {
        // resumeEdit 테이블에 저장
        resumeEditDTO = insertResumeEdit(resumeEditDTO);

        Long resumeEditId = resumeEditDTO.getResumeNo(); // resumeEdit 테이블의 primary key 얻기

        // resume 테이블에 저장
        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setResumeNo(resumeEditId);
        resumeDTO.setContent(content);
        resumeDTO.setUserNo(resumeEditDTO.getUserNo());
        resumeDTO = resumeService.insertResume(resumeDTO);
        Long resumeId = resumeDTO.getResumeNo();

        // mode가 pro(2)인 경우, resume_board 테이블에 저장하고 user mode 2로 변경
        if (mode == 2) {
            ResumeBoardDTO resumeBoardDTO = new ResumeBoardDTO();
            String title = resumeEditDTO.getCompany() + " " + resumeEditDTO.getOccupation();
            resumeBoardDTO.setTitle(title);
            resumeBoardDTO.setResumeNo(resumeId);
            resumeBoardService.insertResumeBoard(resumeBoardDTO);

            if (user.getMode() == 1) { // user의 모드가 1이면 2로 변경
                userService.updateUserMode(resumeEditDTO.getUserNo());
            }
        }

        return resumeEditDTO;
    }

    public ResumeEditDTO convertToDto(ResumeEdit resumeEdit) {
        return modelMapper.map(resumeEdit, ResumeEditDTO.class);
    }
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
