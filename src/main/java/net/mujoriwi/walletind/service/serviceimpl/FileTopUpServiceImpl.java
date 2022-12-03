package net.mujoriwi.walletind.service.serviceimpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.mujoriwi.walletind.model.entity.FileTopUp;
import net.mujoriwi.walletind.model.entity.TopUp;
import net.mujoriwi.walletind.repository.FileTopUpRepository;
import net.mujoriwi.walletind.repository.TopUpRepository;
import net.mujoriwi.walletind.service.service.FileTopUpService;
import net.mujoriwi.walletind.validator.FileTopUpValidator;
import net.mujoriwi.walletind.validator.TopUpValidator;

@Service
public class FileTopUpServiceImpl implements FileTopUpService {

    @Autowired
    private FileTopUpRepository fileTopUpRepository;

    @Autowired
    private TopUpRepository topUpRepository;

    @Autowired
    private TopUpValidator topUpValidator;

    @Autowired
    private FileTopUpValidator fileTopUpValidator;

    private TopUp topUp;
    private FileTopUp fileTopUp;

    @Override
    public FileTopUp store(MultipartFile file, Long topUp_id) throws IOException, Exception {
        Optional<TopUp> topUpOpt = topUpRepository.findById(topUp_id);
        topUpValidator.validateTopUpNotFound(topUpOpt);
        topUp = topUpOpt.get();

        Optional<FileTopUp> fileTopUpOpt = fileTopUpRepository.findByTopUpId(topUp);
        fileTopUpValidator.validateTopUpIdExist(fileTopUpOpt);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileTopUp fileTopUp = new FileTopUp(fileName, file.getContentType(), file.getBytes());
        fileTopUp.setTopUpId(topUp);

        return fileTopUpRepository.save(fileTopUp);
    }

    @Override
    public FileTopUp getFile(Long id) throws Exception {
        Optional<FileTopUp> fileTopUpOpt = fileTopUpRepository.findTopUpId(id);

        fileTopUp = fileTopUpOpt.get();

        return fileTopUpRepository.findById(fileTopUp.getId()).get();
    }

    @Override
    public FileTopUp updateFile(MultipartFile file, Long topUp_id) throws IOException, Exception {
        Optional<TopUp> topUpOpt = topUpRepository.findById(topUp_id);
        topUpValidator.validateTopUpNotFound(topUpOpt);
        topUp = topUpOpt.get();

        Optional<FileTopUp> fileTopUpOpt = fileTopUpRepository.findByTopUpId(topUp);
        fileTopUpValidator.validateTopUpIdNotExist(fileTopUpOpt);

        fileTopUp = fileTopUpOpt.get();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileTopUp.setName(fileName);
        fileTopUp.setType(file.getContentType());
        fileTopUp.setData(file.getBytes());

        return fileTopUpRepository.save(fileTopUp);
    }
}
