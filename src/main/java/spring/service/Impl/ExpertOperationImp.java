package spring.service.Impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ChangeExpertDto;
import spring.dto.ChangePasswordDto;
import spring.dto.RegisterExpertDto;
import spring.dto.ResponceExpertDto;
import spring.entity.Credit;
import spring.entity.Expert;
import spring.enumaration.Role;
import spring.enumaration.Status;
import spring.exception.IoCustomException;
import spring.exception.NotFoundException;
import spring.exception.ValidationException;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.service.ExpertOperation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static spring.service.Impl.CheckInputFromDBOperation.checkUserInfoFromDB;

@Service
@RequiredArgsConstructor
public class ExpertOperationImp implements ExpertOperation {
    private final ExpertGateway expertGateway;
    private final Validator validator;

    public void register(RegisterExpertDto expertDto) {

        if (checkInputIsNotValid(expertDto)) return;

        Expert expert = Mapper.ConvertDtoToEntity.convertExpertDtoToEntity(expertDto);
        expert.setRole(Role.Expert);
        expert.setCredit(Credit.builder().build());
        expert.setPicture(processImageFile(expertDto.picturePath()));
        expert.setPassword(hashPassword(expertDto.password()));
        expertGateway.save(expert);
        System.out.println("expert register done");

    }

    private boolean checkInputIsNotValid(RegisterExpertDto expertDto) {
        Set<ConstraintViolation<RegisterExpertDto>> violations = validator.validate(expertDto);
        Set<String> errors = new HashSet<>();
        errors = checkUserInfoFromDB("Expert",
                expertGateway.existUserByNationalCode(expertDto.nationalCode()),
                expertGateway.existUserByMobileNumber(expertDto.mobileNumber()),
                expertGateway.existUserByEmailAddress(expertDto.emailAddress()),
                expertGateway.existUserByUserName(expertDto.userName()));
        if (!violations.isEmpty() || !errors.isEmpty()) {
            for (ConstraintViolation<RegisterExpertDto> violation : violations) {
                errors.add(violation.getMessage());
            }
            throw new ValidationException(errors);
            //throw new ValidationException(violations); //M.F
           /* if (!experts.isEmpty()) {
                for (String s : experts)
                    System.out.println("\u001B[31m" + s + "\u001B[0m");
            }
            return true;*/
        }
        return false;
    }
    /*private boolean isJpgFile(String filePath) {
        if (filePath.toLowerCase().endsWith(".jpg") || filePath.toLowerCase().endsWith(".jpeg")) {
            //  System.out.println("correct picture path");

            return true;
        }
        return false;
    }*/

    /* private boolean isJpgFileByContent(String filePath) {
         try (FileInputStream fis = new FileInputStream(filePath)) {
             byte[] header = new byte[3];
             int read = fis.read(header);
             if (read != 3) {
                 return false;
             }
             // JPG files have a magic number starting with 0xFFD8FF
             return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
         } catch (FileNotFoundException e) {
             throw new NotFoundException("file not found");
         } catch (IOException e) {
             throw new IoCustomException("Error saving image file", e);
         }
     }
 */
    // Method to check if the file size is less than or equal to 300 KB
    private boolean isFileSizeValid(String filePath) {
        File file = new File(filePath);
        return file.length() <= 300 * 1024; // 300 KB = 300 * 1024 bytes
    }

    // Method to convert the JPG file to a byte array
    private byte[] convertFileToBytes(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            return fileContent;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("file not found");
        } catch (IOException e) {
            throw new IoCustomException("Error saving image file", e);
        }
    }

    public static boolean isImageFileUsingProbeContentType(String inputFilePath) {
        File file = new File(inputFilePath);
        Path filePath = file.toPath();
        String mimeType = null;
        try {
            mimeType = Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new IoCustomException("Error saving image file", e);
        }
        if (mimeType != null && (mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")))
            return true;
        else return false;
        //return mimeType != null && mimeType.startsWith("image/");
    }

    // Method to validate the file and convert it to bytes
    private byte[] processImageFile(String filePath) {
        // if (!isJpgFile(filePath)) {
      /*  if (!isJpgFileByContent(filePath)) {
            throw new IllegalArgumentException("File is not a valid JPG format.");
        }*/
        //  }
        if (!isImageFileUsingProbeContentType(filePath)) {
            // if (!isJpgFile(filePath))
            throw new IllegalArgumentException("File is not a valid JPG format.");
        }
        if (!isFileSizeValid(filePath)) {
            throw new IllegalArgumentException("File size exceeds 300 KB.");
        }
        return convertFileToBytes(filePath);
    }

    private static void convertBytesToFile(byte[] imageBytes, String userName) {
        String outputFilePath = "D:\\Java\\java_inteligent_idea_excercise" +
                                "\\FinalProjectMaktab\\src\\main\\resources\\ExpertPictures\\" + userName + ".jpg";
        File outputFile = new File(outputFilePath);
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            out.write(imageBytes);
            System.out.println("Image saved successfully at " + outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file", e);
        }
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override


    public void update(ChangeExpertDto expertDto) {
        if (validation(expertDto)) return;
        Expert expert1 = Mapper.ConvertDtoToEntity.convertChangeExpertDtoToEntity(expertDto);
        Expert expert = expertGateway.findById(expert1.getId()).orElse(null);
        updateOperation(expertDto, expert);
    }

    private boolean validation(ChangeExpertDto expertDto) {
        Set<ConstraintViolation<ChangeExpertDto>> violations = validator.validate(expertDto);
        boolean exists1 = false;
        boolean exists2 = false;
        boolean exists3 = false;
        boolean exists4 = false;
        if (expertDto.mobileNumber() != null) {
            exists1 = expertGateway.existUserByMobileNumber(expertDto.mobileNumber());
        }
        if (expertDto.nationalCode() != null) {
            exists2 = expertGateway.existUserByNationalCode(expertDto.nationalCode());
        }
        if (expertDto.emailAddress() != null) {
            exists3 = expertGateway.existUserByEmailAddress(expertDto.emailAddress());
        }
        if (expertDto.userName() != null) {
            exists4 = expertGateway.existUserByUserName(expertDto.userName());
        }
        if (!violations.isEmpty() || exists1 || exists2 || exists3 || exists4) {
            for (ConstraintViolation<ChangeExpertDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists1)
                System.out.println("\u001B[31m" + "Expert with this Mobile Number is already exists" + "\u001B[0m");
            if (exists2)
                System.out.println("\u001B[31m" + "Expert with this National Code is already exists" + "\u001B[0m");
            if (exists3)
                System.out.println("\u001B[31m" + "Expert with this Email Address is already exists" + "\u001B[0m");
            if (exists4)
                System.out.println("\u001B[31m" + "Expert with this UserName is already exists" + "\u001B[0m");

            return true;
        }
        return false;
    }

    private void updateOperation(ChangeExpertDto expertDto, Expert expert) {
        if (expert != null) {
            if (expertDto.firstName() != null) {
                expert.setFirstName(expertDto.firstName());
            }
            if (expertDto.lastName() != null) {
                expert.setLastName(expertDto.lastName());
            }
            if (expertDto.mobileNumber() != null) {
                expert.setMobileNumber(expertDto.mobileNumber());
            }
            if (expertDto.nationalCode() != null) {
                expert.setNationalCode(expertDto.nationalCode());
            }
            if (expertDto.emailAddress() != null) {
                expert.setEmailAddress(expertDto.emailAddress());
            }
            if (expertDto.userName() != null) {
                expert.setUserName(expertDto.userName());
            }
            expertGateway.save(expert);
            System.out.println("Expert Updated!");
        } else {
            System.out.println("Expert not found");
        }
    }
    @Transactional//(readOnly = true)
    public /*Optional<*/byte[]/*>*/ getPictureByUserName(String userName) {
        return expertGateway.getPictureByUserName(userName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void getPicture(String userName) {
        /*Optional<*/byte[]/*>*/ pictureOptional  = getPictureByUserName(userName);
      //  if (pictureOptional.isEmpty()) {
        if (pictureOptional==null) {
            throw new NotFoundException("Expert with this User Name not found!");
        }
        convertBytesToFile(pictureOptional, userName);
        /*byte[] picture = pictureOptional.get();
        convertBytesToFile(picture, userName);*/
    }
 /*   @Override
    public void getPictureById(Long id) {
        Optional<Expert> optionalExpert = expertGateway.findById(id);
        if (optionalExpert.isEmpty()) {
            throw new NotFoundException("Expert with this Id not found!");
        }
        Expert expert = optionalExpert.get();
        byte[] picture = expert.getPicture();
        String userName = expert.getUserName();
        convertBytesToFile(picture, userName);
        *//*byte[] picture = pictureOptional.get();
        convertBytesToFile(picture, userName);*//*
    }*/
    @Override
    public void changeExpertStatus(Long expertId, String status) {

        if (status.equalsIgnoreCase(String.valueOf(Status.NEW))
            || status.equalsIgnoreCase(String.valueOf(Status.CONFIRMED)) ||
            status.equalsIgnoreCase(String.valueOf(Status.PENDING_CONFIRMATION))) {
            Expert expert = expertGateway.findById(expertId).orElse(null);
            if (expert != null) {
                status = status.toLowerCase();
                String enumStatus = String.valueOf(expert.getStatus());
                if (!status.equalsIgnoreCase(enumStatus)) {
                    changeStatusOperation(status, expert);
                }
            } else {
                System.err.println("no Expert Found");
            }
        } else {
            System.err.println("enter the valid status");
        }
    }

    private void changeStatusOperation(String status, Expert expert) {
        expert.setStatus(Status.valueOf(status.toUpperCase()));
        expertGateway.save(expert);
        System.out.println("change Status Operation done");
    }


    public void changePassword(ChangePasswordDto passwordDto) {
        if (isNotValid(passwordDto)) return;
        Expert expert = expertGateway.findById(passwordDto.userId()).orElse(null);
        if (expert != null) {
            expert.setPassword(hashPassword(passwordDto.password()));
            expertGateway.save(expert);
            System.out.println("change password done");
        } else System.err.println("Expert not found");
    }

    private boolean isNotValid(ChangePasswordDto passwordDto) {
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(passwordDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<ChangePasswordDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return true;
        }
        return false;
    }

    @Override
    public ResponceExpertDto findById(Long expertId) {
        Expert expert = expertGateway.findById(expertId).orElse(null);
        ResponceExpertDto responceExpertDto = null;
        if (expert != null) {
            responceExpertDto = Mapper.ConvertEntityToDto.convertExpertToDto(expert);
        }
        return responceExpertDto;
    }

}
