package service.Impl;

import dto.RegisterExpertDto;
import entity.Expert;
import enumaration.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import mapper.Mapper;
import repository.BaseEntityRepository;
import service.ExpertService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class ExpertServiceImp implements ExpertService {
    private final BaseEntityRepository<Expert> expertBaseEntityRepository;
    private final Validator validator;

    public ExpertServiceImp(BaseEntityRepository<Expert> expertBaseEntityRepository, Validator validator) {
        this.expertBaseEntityRepository = expertBaseEntityRepository;
        this.validator = validator;
    }


    // Method to check if the file is a JPG based on file extension
    public boolean isJpgFile(String filePath) {
        return filePath.toLowerCase().endsWith(".jpg") || filePath.toLowerCase().endsWith(".jpeg");
    }

    // Method to check if the file is a JPG by checking its content (magic number check)
    public boolean isJpgFileByContent(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            byte[] header = new byte[3];
            if (fis.read(header) != 3) {
                return false;
            }
            // JPG files have a magic number starting with 0xFFD8FF
            return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to check if the file size is less than or equal to 300 KB
    public boolean isFileSizeValid(String filePath) {
        File file = new File(filePath);
        return file.length() <= 300 * 1024; // 300 KB = 300 * 1024 bytes
    }

    // Method to convert the JPG file to a byte array
    public byte[] convertFileToBytes(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            return fileContent;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to validate the file and convert it to bytes
    public byte[] processImageFile(String filePath) {
        if (!isJpgFile(filePath) || !isJpgFileByContent(filePath)) {
            throw new IllegalArgumentException("File is not a valid JPG format.");
        }

        if (!isFileSizeValid(filePath)) {
            throw new IllegalArgumentException("File size exceeds 300 KB.");
        }
        return convertFileToBytes(filePath);
    }


    @Override
    public void save(RegisterExpertDto expertDto) {
        Set<ConstraintViolation<RegisterExpertDto>> violations = validator.validate(expertDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterExpertDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return;
        }
        Expert expert = Mapper.convertExpertDtoToEntity(expertDto);
        expert.setRole(Role.Expert);
        try {
            expertBaseEntityRepository.save(expert);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
