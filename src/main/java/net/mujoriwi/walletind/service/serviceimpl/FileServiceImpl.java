package net.mujoriwi.walletind.service.serviceimpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import net.mujoriwi.walletind.model.entity.FileEntity;
import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.repository.FileRepository;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.FileService;
import net.mujoriwi.walletind.validator.FileValidator;
import net.mujoriwi.walletind.validator.UserValidator;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileValidator fileValidator;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    private FileEntity fileEntity;

    private User user;

    @Override
    public FileEntity store(MultipartFile file, Long user_id) throws IOException, Exception {
        // TODO Auto-generated method stub
        Optional<User> userOpt = userRepository.findById(user_id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        Optional<FileEntity> fileEntityOpt = fileRepository.findByUserId(user);
        fileValidator.validateUserIdExist(fileEntityOpt);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes());
        fileEntity.setUserId(user);
        return fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity updateFile(MultipartFile file, Long user_id, String id) throws IOException, Exception {
        Optional<User> userOpt = userRepository.findById(user_id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        Optional<FileEntity> fileEntityOpt = fileRepository.findById(id);
        fileEntity = fileEntityOpt.get();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileEntity.setName(fileName);
        fileEntity.setType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setUserId(user);
        return fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity getFile(String id) {
        // TODO Auto-generated method stub
        return fileRepository.findById(id).get();
    }

}
